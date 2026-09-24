#!/usr/bin/env bash
# Compile every solution and run all six topic-day test suites.
set -euo pipefail
cd "$(dirname "$0")"
rm -rf out
javac -Xlint:none -d out $(find . -name '*.java')
status=0
for suite in TestMon TestTue TestWed TestThu TestFri TestSat; do
  printf '%-8s ' "$suite"
  java -cp out "$suite" 2>&1 | grep -v JAVA_TOOL_OPTIONS || status=1
done
exit $status
