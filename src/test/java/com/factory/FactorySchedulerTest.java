package com.factory;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for FactoryScheduler.solve()
 *
 * Test categories:
 *   1. Possible cases    — sum divisible by N-1, k >= max(A)
 *   2. Impossible cases  — sum not divisible, or k < max(A)
 *   3. Edge cases        — N=1, all zeros, single non-zero element
 */
public class FactorySchedulerTest {

    // -------------------------------------------------------------------------
    // Possible cases
    // -------------------------------------------------------------------------

    @Test
    public void test_N4_equalTargets_possible() {
        // N=4, A=[3,3,3,3]: sum=12, denom=3, k=4, max=3, k>=max => possible
        long result = FactoryScheduler.solve(4, new long[]{3, 3, 3, 3});
        assertEquals(4, result);
    }

    @Test
    public void test_N4_mixedTargets() {
        // N=4, A=[2,2,1,1]: sum=6, denom=3, k=2, max=2 => possible
        long result = FactoryScheduler.solve(4, new long[]{2, 2, 1, 1});
        assertEquals(2, result);
    }

    @Test
    public void test_N5_mixedTargets() {
        // N=5, A=[2,2,2,1,1]: sum=8, denom=4, k=2, max=2 => possible
        long result = FactoryScheduler.solve(5, new long[]{2, 2, 2, 1, 1});
        assertEquals(2, result);
    }

    @Test
    public void test_N2_anyTargetIsPossible() {
        // N=2: denom=1, k = sum, max = max element; k = A[0]+A[1] >= max always
        long result = FactoryScheduler.solve(2, new long[]{5, 3});
        assertEquals(8, result);
    }

    @Test
    public void test_allZeros() {
        // All targets are 0 => B is already A, 0 cycles needed
        long result = FactoryScheduler.solve(3, new long[]{0, 0, 0});
        assertEquals(0, result);
    }

    @Test
    public void test_largeValues() {
        // Large inputs should not overflow (using long)
        long result = FactoryScheduler.solve(3, new long[]{1_000_000_000L, 1_000_000_000L, 0});
        assertEquals(1_000_000_000L, result);
    }

    // -------------------------------------------------------------------------
    // Impossible cases
    // -------------------------------------------------------------------------

    @Test
    public void test_N3_sumNotDivisible() {
        // N=3, A=[1,1,1]: sum=3, denom=2, 3%2 != 0 => impossible
        long result = FactoryScheduler.solve(3, new long[]{1, 1, 1});
        assertEquals(-1, result);
    }

    @Test
    public void test_N4_sumNotDivisible() {
        // N=4, A=[2,2,2,2]: sum=8, denom=3, 8%3 != 0 => impossible
        long result = FactoryScheduler.solve(4, new long[]{2, 2, 2, 2});
        assertEquals(-1, result);
    }

    @Test
    public void test_kLessThanMax() {
        // N=4, A=[3,3,0,0]: sum=6, k=2, max=3, k < max => impossible
        long result = FactoryScheduler.solve(4, new long[]{3, 3, 0, 0});
        assertEquals(-1, result);
    }

    @Test
    public void test_N3_impossible_zeroElement() {
        // N=3, A=[2,1,0]: sum=3, denom=2, 3%2 != 0 => impossible
        long result = FactoryScheduler.solve(3, new long[]{2, 1, 0});
        assertEquals(-1, result);
    }

    // -------------------------------------------------------------------------
    // Edge cases — N=1
    // -------------------------------------------------------------------------

    @Test
    public void test_N1_targetZero() {
        // N=1: operation picks 0 indices, B[0] stays 0. Only A=[0] is valid.
        long result = FactoryScheduler.solve(1, new long[]{0});
        assertEquals(0, result);
    }

    @Test
    public void test_N1_targetNonZero() {
        // N=1: can never increment anything => impossible
        long result = FactoryScheduler.solve(1, new long[]{5});
        assertEquals(-1, result);
    }

    // -------------------------------------------------------------------------
    // skipCounts helper
    // -------------------------------------------------------------------------

    @Test
    public void test_skipCounts_possible() {
        long[] skips = FactoryScheduler.skipCounts(4, new long[]{2, 2, 1, 1});
        // k=2, skips = [2-2, 2-2, 2-1, 2-1] = [0, 0, 1, 1]
        assertNotNull(skips);
        assertArrayEquals(new long[]{0, 0, 1, 1}, skips);
    }

    @Test
    public void test_skipCounts_impossible() {
        long[] skips = FactoryScheduler.skipCounts(3, new long[]{1, 1, 1});
        assertNull(skips);
    }
}
