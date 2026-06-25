# Factory Cycle Scheduler

A clean O(N) algorithm that determines the minimum number of production cycles for a factory to hit exact per-station run targets.

## The scenario

A factory has **N stations**. In every production cycle, exactly **N−1 stations** are run — one station is always skipped for maintenance or diagnostics. Every station starts at 0 run-count.

Management provides a target: after some number of cycles, station `i` must have been run exactly `A[i]` times.

**Task:** Is this achievable? If yes, how many cycles does it take?

## Why this is non-trivial

A naive approach might try to simulate every possible combination of which station to skip each cycle. That explodes combinatorially. Instead, the solution uses a single mathematical invariant to answer in O(N) time.

## Core insight

Let `k` = number of cycles performed.

- Each cycle increments exactly `N−1` stations (skips one).
- Station `i` is run `k − x_i` times, where `x_i` is how many times it was the skipped station.
- So `A[i] = k − x_i`, which gives `x_i = k − A[i]`.
- Since each cycle skips exactly one station, the total skips across all stations = k:

```
x_1 + x_2 + ... + x_N = k
=> sum(k − A[i]) = k
=> N*k − sum(A) = k
=> (N−1)*k = sum(A)
=> k = sum(A) / (N−1)
```

## Feasibility conditions

1. `sum(A)` must be divisible by `(N−1)` — otherwise `k` is not an integer.
2. `k >= max(A)` — a station can be run at most once per cycle, so no target can exceed `k`.

If both hold → answer is `k`. Otherwise → `-1` (impossible).

## Algorithm

```
solve(N, A):
  if N == 1:
    return 0 if A[0] == 0, else -1
  sumA = sum of all A[i]
  maxA = max of all A[i]
  if sumA % (N-1) != 0: return -1
  k = sumA / (N-1)
  if k < maxA: return -1
  return k
```

**Time:** O(N) · **Space:** O(1)

## Project structure

```
FactoryScheduler/
├── pom.xml
└── src/
    ├── main/java/com/factory/
    │   ├── FactoryScheduler.java   ← core solver + skip-count helper
    │   └── Main.java               ← I/O driver with fast scanner
    └── test/java/com/factory/
        └── FactorySchedulerTest.java ← 13 unit tests
```

## Building and running

**Compile and test:**
```bash
mvn test
```

**Build runnable JAR:**
```bash
mvn package
```

**Run:**
```bash
echo "2
4
2 2 1 1
3
1 1 1" | java -jar target/factory-scheduler.jar
```

**Expected output:**
```
2
-1
```

## Sample test cases

| N | A              | sum(A) | k = sum/(N-1) | max(A) | result |
|---|----------------|--------|----------------|--------|--------|
| 4 | [2, 2, 1, 1]  | 6      | 6/3 = 2        | 2      | **2**  |
| 5 | [2, 2, 2, 1, 1] | 8   | 8/4 = 2        | 2      | **2**  |
| 4 | [3, 3, 3, 3]  | 12     | 12/3 = 4       | 3      | **4**  |
| 3 | [1, 1, 1]     | 3      | 3/2 = 1.5      | —      | **-1** (not integer) |
| 4 | [3, 3, 0, 0]  | 6      | 6/3 = 2        | 3      | **-1** (k < max) |

## What this demonstrates

- Translating a simulation problem into a **mathematical invariant**
- Deriving a **closed-form O(N) solution** instead of brute-force search
- Clean separation of concerns: solver logic vs. I/O driver
- Handling of edge cases (N=1, all-zero targets, overflow-safe `long` arithmetic)
