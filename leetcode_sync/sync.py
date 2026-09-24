"""Sync accepted LeetCode submissions into the DSA-2026 and Daily Log Notion databases.

Runs from the GitHub Action in .github/workflows/leetcode-sync.yml. Uses only the
standard library so the workflow needs no installs.

Environment:
  LEETCODE_USERNAME     your LeetCode username (required)
  NOTION_TOKEN          Notion internal integration secret (required)
  DSA_DATABASE_ID       DSA-2026 database id
  DAILY_LOG_DATABASE_ID Daily Log database id
  TIMEZONE              IANA timezone used to decide which day a solve counts for
  DRY_RUN               "1" to print what would change without writing to Notion
"""

from __future__ import annotations

import json
import os
import sys
import urllib.error
import urllib.request
from dataclasses import dataclass
from datetime import date, datetime
from zoneinfo import ZoneInfo

LEETCODE_GRAPHQL = "https://leetcode.com/graphql"
NOTION_API = "https://api.notion.com/v1"
NOTION_VERSION = "2022-06-28"

DEFAULT_DSA_DB = "2e2ae31445f88061916dd3d020c29bd2"
DEFAULT_DAILY_LOG_DB = "0180c51a29024bc68ab2945552dd2057"

DIFFICULTY = {"Easy": "🧩Easy", "Medium": "🧠Medium", "Hard": "🔥Hard"}

# LeetCode topic tag -> existing DSA-2026 "Data Structure" option (first match wins)
DATA_STRUCTURE_BY_TAG = [
    ("Linked List", "Linked List"),
    ("Tree", "Tree"),
    ("Binary Tree", "Tree"),
    ("Binary Search Tree", "Tree"),
    ("Heap (Priority Queue)", "Heap"),
    ("Dynamic Programming", "Dynamic Programming"),
    ("Hash Table", "Hash"),
]

# LeetCode topic tag -> existing DSA-2026 "Pattern" option (first match wins)
PATTERN_BY_TAG = [
    ("Topological Sort", "Topological Sort"),
    ("Sliding Window", "Sliding Window"),
    ("Binary Search", "Modified Binary Search"),
    ("Two Pointers", "Two Pointers"),
    ("Backtracking", "Subset"),
    ("Breadth-First Search", "Binary Tree BFS"),
    ("Depth-First Search", "Binary Tree DFS"),
]

SYNC_TAG = "LeetCode sync"


@dataclass
class Submission:
    title: str
    slug: str
    timestamp: int

    @property
    def link(self) -> str:
        return problem_link(self.slug)


def problem_link(slug: str) -> str:
    return f"https://leetcode.com/problems/{slug}/"


def _post_json(url: str, payload: dict, headers: dict, method: str = "POST") -> dict:
    req = urllib.request.Request(
        url,
        data=json.dumps(payload).encode(),
        headers={"Content-Type": "application/json", **headers},
        method=method,
    )
    try:
        with urllib.request.urlopen(req, timeout=30) as resp:
            return json.loads(resp.read().decode())
    except urllib.error.HTTPError as e:
        body = e.read().decode(errors="replace")
        raise RuntimeError(f"{method} {url} failed with {e.code}: {body}") from None


class LeetCode:
    HEADERS = {
        "Referer": "https://leetcode.com/",
        "User-Agent": "Mozilla/5.0 (placementprep-leetcode-sync)",
    }

    def _query(self, query: str, variables: dict) -> dict:
        res = _post_json(LEETCODE_GRAPHQL, {"query": query, "variables": variables}, self.HEADERS)
        if res.get("errors"):
            raise RuntimeError(f"LeetCode GraphQL error: {res['errors']}")
        return res["data"]

    def recent_accepted(self, username: str, limit: int = 20) -> list[Submission]:
        data = self._query(
            """query recentAc($username: String!, $limit: Int!) {
                 recentAcSubmissionList(username: $username, limit: $limit) {
                   title titleSlug timestamp
                 }
               }""",
            {"username": username, "limit": limit},
        )
        rows = data.get("recentAcSubmissionList") or []
        return [Submission(r["title"], r["titleSlug"], int(r["timestamp"])) for r in rows]

    def question(self, slug: str) -> dict:
        data = self._query(
            """query q($titleSlug: String!) {
                 question(titleSlug: $titleSlug) {
                   questionFrontendId title difficulty topicTags { name }
                 }
               }""",
            {"titleSlug": slug},
        )
        return data["question"]


class Notion:
    def __init__(self, token: str, dry_run: bool = False):
        self.headers = {"Authorization": f"Bearer {token}", "Notion-Version": NOTION_VERSION}
        self.dry_run = dry_run

    def query(self, database_id: str, filter_: dict) -> list[dict]:
        results, cursor = [], None
        while True:
            body = {"filter": filter_, "page_size": 100}
            if cursor:
                body["start_cursor"] = cursor
            res = _post_json(f"{NOTION_API}/databases/{database_id}/query", body, self.headers)
            results.extend(res["results"])
            if not res.get("has_more"):
                return results
            cursor = res["next_cursor"]

    def create(self, database_id: str, properties: dict) -> None:
        if self.dry_run:
            print(f"  [dry run] create in {database_id}: {json.dumps(properties, ensure_ascii=False)}")
            return
        _post_json(f"{NOTION_API}/pages", {"parent": {"database_id": database_id}, "properties": properties}, self.headers)

    def update(self, page_id: str, properties: dict) -> None:
        if self.dry_run:
            print(f"  [dry run] update {page_id}: {json.dumps(properties, ensure_ascii=False)}")
            return
        _post_json(f"{NOTION_API}/pages/{page_id}", {"properties": properties}, self.headers, method="PATCH")


def first_match(tags: list[str], table: list[tuple[str, str]]) -> str | None:
    for tag, option in table:
        if tag in tags:
            return option
    return None


def solved_date(timestamp: int, tz: ZoneInfo) -> date:
    return datetime.fromtimestamp(timestamp, tz).date()


def earliest_per_problem(subs: list[Submission]) -> list[Submission]:
    """Keep one submission per problem: the earliest accepted one in the window."""
    best: dict[str, Submission] = {}
    for s in subs:
        if s.slug not in best or s.timestamp < best[s.slug].timestamp:
            best[s.slug] = s
    return sorted(best.values(), key=lambda s: s.timestamp)


def new_problem_properties(q: dict, sub: Submission, solved_on: date) -> dict:
    tags = [t["name"] for t in q.get("topicTags") or []]
    props = {
        "Task name": {"title": [{"text": {"content": f"{q['questionFrontendId']}. {q['title']}"}}]},
        "Status": {"status": {"name": "Done"}},
        "LeetCode link": {"url": sub.link},
        "Solved on": {"date": {"start": solved_on.isoformat()}},
        "Questions List": {"multi_select": [{"name": SYNC_TAG}]},
    }
    if q["questionFrontendId"].isdigit():
        props["No"] = {"number": int(q["questionFrontendId"])}
    if q.get("difficulty") in DIFFICULTY:
        props["Difficulty"] = {"select": {"name": DIFFICULTY[q["difficulty"]]}}
    if ds := first_match(tags, DATA_STRUCTURE_BY_TAG):
        props["Data Structure"] = {"select": {"name": ds}}
    if pattern := first_match(tags, PATTERN_BY_TAG):
        props["Pattern"] = {"select": {"name": pattern}}
    return props


def sync_problems(lc: LeetCode, notion: Notion, dsa_db: str, subs: list[Submission], tz: ZoneInfo) -> set[date]:
    """Add or complete each solved problem in DSA-2026. Returns the days that changed."""
    touched: set[date] = set()
    for sub in earliest_per_problem(subs):
        day = solved_date(sub.timestamp, tz)
        existing = notion.query(dsa_db, {"property": "LeetCode link", "url": {"equals": sub.link}})
        if existing:
            page = existing[0]
            props = page["properties"]
            status = ((props.get("Status") or {}).get("status") or {}).get("name")
            has_date = bool((props.get("Solved on") or {}).get("date"))
            if status == "Done" and has_date:
                continue
            print(f"✓ {sub.title}: marking Done (solved {day})")
            notion.update(page["id"], {
                "Status": {"status": {"name": "Done"}},
                "Solved on": {"date": {"start": day.isoformat()}},
            })
        else:
            print(f"+ {sub.title}: adding to DSA-2026 (solved {day})")
            notion.create(dsa_db, new_problem_properties(lc.question(sub.slug), sub, day))
        touched.add(day)
    return touched


def update_daily_log(notion: Notion, dsa_db: str, log_db: str, days: set[date]) -> None:
    """Set 'DSA solved' on each touched day's Daily Log row to the number of problems solved that day."""
    for day in sorted(days):
        solved = notion.query(dsa_db, {"property": "Solved on", "date": {"equals": day.isoformat()}})
        count = len(solved)
        rows = notion.query(log_db, {"property": "Date", "date": {"equals": day.isoformat()}})
        if rows:
            print(f"  Daily Log {day}: DSA solved = {count}")
            notion.update(rows[0]["id"], {"DSA solved": {"number": count}})
        else:
            print(f"  Daily Log {day}: new row, DSA solved = {count}")
            notion.create(log_db, {
                "Day": {"title": [{"text": {"content": day.strftime("%a %d %b")}}]},
                "Date": {"date": {"start": day.isoformat()}},
                "DSA solved": {"number": count},
            })


def main() -> int:
    username = os.environ.get("LEETCODE_USERNAME", "").strip()
    token = os.environ.get("NOTION_TOKEN", "").strip()
    if not username or not token:
        print("LEETCODE_USERNAME and NOTION_TOKEN must be set.", file=sys.stderr)
        return 2
    dsa_db = os.environ.get("DSA_DATABASE_ID") or DEFAULT_DSA_DB
    log_db = os.environ.get("DAILY_LOG_DATABASE_ID") or DEFAULT_DAILY_LOG_DB
    tz = ZoneInfo(os.environ.get("TIMEZONE") or "Asia/Kolkata")
    notion = Notion(token, dry_run=os.environ.get("DRY_RUN") == "1")
    lc = LeetCode()

    subs = lc.recent_accepted(username)
    print(f"Found {len(subs)} recent accepted submissions for {username}.")
    touched = sync_problems(lc, notion, dsa_db, subs, tz)
    update_daily_log(notion, dsa_db, log_db, touched)
    print(f"Done. {len(touched)} day(s) updated.")
    return 0


if __name__ == "__main__":
    sys.exit(main())
