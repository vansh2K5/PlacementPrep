import unittest
from datetime import date
from zoneinfo import ZoneInfo

from leetcode_sync import sync
from leetcode_sync.sync import Submission

IST = ZoneInfo("Asia/Kolkata")
# 2026-09-28 20:00 IST and 2026-09-28 23:30 IST
TS_MON_EVENING = 1790605800
TS_MON_LATE = 1790618400
# 2026-09-29 00:30 IST (still Monday night in UTC, but Tuesday in India)
TS_TUE_EARLY = 1790622000


class FakeLeetCode:
    def __init__(self, questions):
        self.questions = questions
        self.asked = []

    def question(self, slug):
        self.asked.append(slug)
        return self.questions[slug]


class FakeNotion:
    """In-memory stand-in for the two databases, supporting the filters sync.py uses."""

    def __init__(self, dsa_rows=None, log_rows=None):
        self.db = {"dsa": list(dsa_rows or []), "log": list(log_rows or [])}
        self.created, self.updated = [], []

    def query(self, database_id, filter_):
        prop = filter_["property"]
        rows = self.db[database_id]
        if "url" in filter_:
            want = filter_["url"]["equals"]
            return [r for r in rows if (r["properties"].get(prop) or {}).get("url") == want]
        want = filter_["date"]["equals"]
        return [r for r in rows if ((r["properties"].get(prop) or {}).get("date") or {}).get("start") == want]

    def create(self, database_id, properties):
        self.created.append((database_id, properties))
        self.db[database_id].append({"id": f"new-{len(self.created)}", "properties": properties})

    def update(self, page_id, properties):
        self.updated.append((page_id, properties))
        for rows in self.db.values():
            for r in rows:
                if r["id"] == page_id:
                    r["properties"].update(properties)


def plan_row(page_id, slug, status="Not started"):
    return {"id": page_id, "properties": {
        "LeetCode link": {"url": sync.problem_link(slug)},
        "Status": {"status": {"name": status}},
        "Solved on": {"date": None},
    }}


TWO_SUM = {"questionFrontendId": "1", "title": "Two Sum", "difficulty": "Easy",
           "topicTags": [{"name": "Array"}, {"name": "Hash Table"}]}


class SolvedDateTest(unittest.TestCase):
    def test_uses_local_timezone(self):
        self.assertEqual(sync.solved_date(TS_MON_LATE, IST), date(2026, 9, 28))
        self.assertEqual(sync.solved_date(TS_TUE_EARLY, IST), date(2026, 9, 29))


class EarliestPerProblemTest(unittest.TestCase):
    def test_keeps_first_accept_of_each_problem(self):
        subs = [Submission("Two Sum", "two-sum", TS_MON_LATE),
                Submission("Two Sum", "two-sum", TS_MON_EVENING),
                Submission("3Sum", "3sum", TS_TUE_EARLY)]
        out = sync.earliest_per_problem(subs)
        self.assertEqual([(s.slug, s.timestamp) for s in out],
                         [("two-sum", TS_MON_EVENING), ("3sum", TS_TUE_EARLY)])


class NewProblemPropertiesTest(unittest.TestCase):
    def test_maps_difficulty_number_and_tags(self):
        props = sync.new_problem_properties(TWO_SUM, Submission("Two Sum", "two-sum", 0), date(2026, 9, 28))
        self.assertEqual(props["Task name"]["title"][0]["text"]["content"], "1. Two Sum")
        self.assertEqual(props["No"], {"number": 1})
        self.assertEqual(props["Difficulty"], {"select": {"name": "🧩Easy"}})
        self.assertEqual(props["Data Structure"], {"select": {"name": "Hash"}})
        self.assertNotIn("Pattern", props)
        self.assertEqual(props["Status"], {"status": {"name": "Done"}})
        self.assertEqual(props["Solved on"], {"date": {"start": "2026-09-28"}})
        self.assertEqual(props["LeetCode link"], {"url": "https://leetcode.com/problems/two-sum/"})

    def test_pattern_from_tags(self):
        q = {"questionFrontendId": "3", "title": "Longest Substring", "difficulty": "Medium",
             "topicTags": [{"name": "Hash Table"}, {"name": "Sliding Window"}]}
        props = sync.new_problem_properties(q, Submission("x", "x", 0), date(2026, 9, 28))
        self.assertEqual(props["Pattern"], {"select": {"name": "Sliding Window"}})


class SyncProblemsTest(unittest.TestCase):
    def test_completes_planned_problem_instead_of_duplicating(self):
        notion = FakeNotion(dsa_rows=[plan_row("p1", "two-sum")])
        lc = FakeLeetCode({})
        days = sync.sync_problems(lc, notion, "dsa", [Submission("Two Sum", "two-sum", TS_MON_EVENING)], IST)
        self.assertEqual(days, {date(2026, 9, 28)})
        self.assertEqual(notion.created, [])
        self.assertEqual(lc.asked, [])
        self.assertEqual(notion.updated[0][0], "p1")
        self.assertEqual(notion.updated[0][1]["Solved on"], {"date": {"start": "2026-09-28"}})

    def test_adds_unplanned_problem(self):
        notion = FakeNotion()
        lc = FakeLeetCode({"two-sum": TWO_SUM})
        sync.sync_problems(lc, notion, "dsa", [Submission("Two Sum", "two-sum", TS_MON_EVENING)], IST)
        self.assertEqual(len(notion.created), 1)
        self.assertEqual(notion.created[0][0], "dsa")

    def test_already_synced_problem_is_skipped(self):
        row = plan_row("p1", "two-sum", status="Done")
        row["properties"]["Solved on"] = {"date": {"start": "2026-09-20"}}
        notion = FakeNotion(dsa_rows=[row])
        days = sync.sync_problems(FakeLeetCode({}), notion, "dsa",
                                  [Submission("Two Sum", "two-sum", TS_MON_EVENING)], IST)
        self.assertEqual(days, set())
        self.assertEqual(notion.updated, [])

    def test_running_twice_changes_nothing_the_second_time(self):
        notion = FakeNotion()
        lc = FakeLeetCode({"two-sum": TWO_SUM})
        subs = [Submission("Two Sum", "two-sum", TS_MON_EVENING)]
        sync.sync_problems(lc, notion, "dsa", subs, IST)
        self.assertEqual(sync.sync_problems(lc, notion, "dsa", subs, IST), set())
        self.assertEqual(len(notion.created), 1)


class DailyLogTest(unittest.TestCase):
    def test_creates_row_with_count(self):
        solved = plan_row("p1", "two-sum", "Done")
        solved["properties"]["Solved on"] = {"date": {"start": "2026-09-28"}}
        other = plan_row("p2", "3sum", "Done")
        other["properties"]["Solved on"] = {"date": {"start": "2026-09-28"}}
        notion = FakeNotion(dsa_rows=[solved, other])
        sync.update_daily_log(notion, "dsa", "log", {date(2026, 9, 28)})
        db, props = notion.created[0]
        self.assertEqual(db, "log")
        self.assertEqual(props["DSA solved"], {"number": 2})
        self.assertEqual(props["Date"], {"date": {"start": "2026-09-28"}})
        self.assertEqual(props["Day"]["title"][0]["text"]["content"], "Mon 28 Sep")

    def test_updates_existing_row(self):
        solved = plan_row("p1", "two-sum", "Done")
        solved["properties"]["Solved on"] = {"date": {"start": "2026-09-28"}}
        log = {"id": "l1", "properties": {"Date": {"date": {"start": "2026-09-28"}}, "Study hours": {"number": 8}}}
        notion = FakeNotion(dsa_rows=[solved], log_rows=[log])
        sync.update_daily_log(notion, "dsa", "log", {date(2026, 9, 28)})
        self.assertEqual(notion.created, [])
        self.assertEqual(notion.updated, [("l1", {"DSA solved": {"number": 1}})])


if __name__ == "__main__":
    unittest.main()
