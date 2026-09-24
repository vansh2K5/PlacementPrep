# PlacementPrep

Tools behind my placement-season Notion workspace.

## Solutions (`solutions/`)

Java solutions for all 72 problems in the 4-week LeetCode plan, one package per problem (`p0001` = LC 1, etc.), in the exact `class Solution` form LeetCode expects. The same code, with a hint and an in-depth walkthrough (intuition, brute force → optimal, dry run, complexity, pitfalls, follow-ups), is inside each problem's page in the **DSA-2026** Notion database under the **💡 Solution** toggle.

Every solution is tested. Run all 203 test cases with:

```bash
solutions/run-tests.sh
```

## LeetCode → Notion sync

A GitHub Action (`.github/workflows/leetcode-sync.yml`) runs every 4 hours and:

1. Reads your recent accepted LeetCode submissions (public API, no login needed).
2. For each solved problem, finds it in the **DSA-2026** database by its `LeetCode link`.
   - Already planned (e.g. from the 4-week LeetCode plan): marks it **Done** and sets **Solved on**.
   - Not in the database yet: adds it with number, title, difficulty, link, data structure/pattern (from LeetCode's tags) and the `LeetCode sync` tag.
3. Sets **DSA solved** in the **Daily Log** row for that day, which feeds the progress charts on the timetable page.

Running it again never creates duplicates.

### One-time setup (about 5 minutes)

1. **Create a Notion integration.** Go to <https://www.notion.so/profile/integrations>, click **New integration**, choose **Internal**, and give it read + update + insert content access. Copy the **Internal Integration Secret**.
2. **Give it access to the two databases.** Open **DSA-2026** in Notion → `•••` menu → **Connections** → add your integration. Do the same for **Daily Log** (inside the *Placement Prep Timetable* page).
3. **Add the secret and settings to this repo.** GitHub → **Settings → Secrets and variables → Actions**:
   - **Secrets** tab: `NOTION_TOKEN` = the secret from step 1.
   - **Variables** tab: `LEETCODE_USERNAME` = your LeetCode username.
   - Optional variables: `TIMEZONE` (default `Asia/Kolkata`), `DSA_DATABASE_ID`, `DAILY_LOG_DATABASE_ID` (defaults point at the current databases).
4. **Test it.** **Actions → LeetCode → Notion sync → Run workflow**. Tick *dry run* first to see what it would change, then run it for real.

### Limits

- LeetCode's public API only returns your **last 20 accepted submissions**, so if you solve more than 20 problems in 4 hours, the oldest ones in that burst are missed. Run the workflow manually after a big session if needed.
- A problem counts on the day of its **first** accepted submission in that window, in your `TIMEZONE`.
- The sync overwrites **DSA solved** in the Daily Log for the days it touches. Your other Daily Log fields are left alone.

### Run locally

```bash
LEETCODE_USERNAME=you NOTION_TOKEN=secret_xxx DRY_RUN=1 python -m leetcode_sync.sync
python -m unittest leetcode_sync.test_sync
```
