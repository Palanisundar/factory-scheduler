package com.factory;

/**
 * FactoryScheduler
 *
 * Problem:
 *   A factory has N stations. In each production cycle, exactly N-1 stations
 *   are run — one station is skipped for maintenance/diagnostics. All stations
 *   start at 0 run-count. Given a target run count A[i] for each station, this
 *   class computes the minimum number of cycles needed to hit all targets exactly.
 *
 * Core invariant:
 *   Each cycle adds 1 to exactly (N-1) stations, so total increments per cycle = N-1.
 *   After k cycles: (N-1) * k = sum(A)
 *   => k = sum(A) / (N-1)
 *
 *   Also, each station can be run at most once per cycle, so A[i] <= k for all i.
 *
 * Complexity: O(N) time, O(1) extra space.
 */
public class FactoryScheduler {

    /**
     * Computes the number of production cycles required.
     *
     * @param N the number of stations
     * @param A target run counts; A[i] = how many times station i must be run
     * @return  minimum cycles required, or -1 if impossible
     */
    public static long solve(int N, long[] A) {
        if (N == 1) {
            // Each operation increments 0 out of 1 stations → B stays 0 forever.
            // Only valid if the single target is already 0.
            return (A[0] == 0) ? 0 : -1;
        }

        long sumA = 0;
        long maxA = 0;

        for (long v : A) {
            sumA += v;
            if (v > maxA) maxA = v;
        }

        long denom = N - 1;

        // Condition 1: sum(A) must be divisible by (N-1)
        // Reason: each cycle contributes exactly (N-1) increments total,
        //         so total increments after k cycles = (N-1)*k = sum(A).
        if (sumA % denom != 0) {
            return -1;
        }

        long k = sumA / denom;

        // Condition 2: no station can need more runs than the number of cycles.
        // A station is run in at most every cycle (it can be the skipped one 0 times).
        // So A[i] <= k must hold for all i.
        if (k < maxA) {
            return -1;
        }

        return k;
    }

    /**
     * Computes how many times each station is skipped in the optimal schedule.
     * Returns null if the schedule is impossible (solve returns -1).
     *
     * skip[i] = k - A[i]  (number of cycles where station i is the skipped one)
     *
     * @param N the number of stations
     * @param A target run counts
     * @return  skip counts per station, or null if impossible
     */
    public static long[] skipCounts(int N, long[] A) {
        long k = solve(N, A);
        if (k == -1) return null;

        long[] skips = new long[N];
        for (int i = 0; i < N; i++) {
            skips[i] = k - A[i];
        }
        return skips;
    }
}
