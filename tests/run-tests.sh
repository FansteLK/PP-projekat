#!/usr/bin/env bash
# Golden-file tests for the MicroJava compiler.
#   examples/*.mj         must compile; running the program (stdin from tests/input/<name>.in)
#                         must print exactly tests/expected/<name>.out
#   examples/errors/*.mj  must be rejected; diagnostics must match tests/expected/errors/<name>.err
# Run with UPDATE=1 to regenerate the expected files after an intentional change.
set -uo pipefail
cd "$(dirname "$0")/.."

MJC="java -jar build/mjc.jar"
RUN="java -cp lib/mj-runtime-1.1.jar rs.etf.pp1.mj.runtime.Run"
OUT=build/test
mkdir -p "$OUT/errors" tests/expected/errors
pass=0; fail=0

check() {  # name, expected-file, actual-file
  if [[ "${UPDATE:-}" == 1 ]]; then cp "$3" "$2"; fi
  if diff -u "$2" "$3" > "$OUT/$1.diff"; then
    pass=$((pass + 1)); echo "  ok    $1"
  else
    fail=$((fail + 1)); echo "  FAIL  $1"; cat "$OUT/$1.diff"
  fi
}

echo "Valid programs:"
for src in examples/*.mj; do
  name=$(basename "$src" .mj)
  input=tests/input/$name.in; [[ -f $input ]] || input=/dev/null
  if ! $MJC "$src" "$OUT/$name.obj" 2> "$OUT/$name.compile"; then
    fail=$((fail + 1)); echo "  FAIL  $name (did not compile)"; cat "$OUT/$name.compile"; continue
  fi
  if grep -q . "$OUT/$name.compile" && grep -qv -- ' -> ' "$OUT/$name.compile"; then
    fail=$((fail + 1)); echo "  FAIL  $name (unexpected diagnostics)"; cat "$OUT/$name.compile"; continue
  fi
  # The VM appends a timing line; drop it so output is deterministic.
  $RUN "$OUT/$name.obj" < "$input" 2>&1 | sed '/^Completion took/d; s/Completion took [0-9]* ms$//' > "$OUT/$name.out"
  check "$name" "tests/expected/$name.out" "$OUT/$name.out"
done

echo "Programs with errors:"
for src in examples/errors/*.mj; do
  name=$(basename "$src" .mj)
  if $MJC "$src" "$OUT/$name.obj" 2> "$OUT/$name.err"; then
    fail=$((fail + 1)); echo "  FAIL  $name (compiled, expected errors)"; continue
  fi
  check "errors/$name" "tests/expected/errors/$name.err" "$OUT/$name.err"
done

echo "$pass passed, $fail failed"
[[ $fail == 0 ]]
