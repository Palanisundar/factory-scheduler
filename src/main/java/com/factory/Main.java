package com.factory;

import java.io.*;
import java.util.*;

/**
 * Main entry point for the Factory Cycle Scheduler.
 *
 * Input format:
 *   Line 1:  T (number of test cases)
 *   For each test case:
 *     Line 1: N (number of stations)
 *     Line 2: N space-separated integers A[1] A[2] ... A[N]
 *
 * Output format:
 *   For each test case: minimum number of cycles, or -1 if impossible.
 *
 * Example input:
 *   4
 *   4
 *   2 2 1 1
 *   3
 *   1 1 1
 *   5
 *   2 2 2 1 1
 *   4
 *   3 3 3 3
 *
 * Example output:
 *   2
 *   -1
 *   2
 *   -1
 */
public class Main {

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int T = fs.nextInt();
        while (T-- > 0) {
            int N = fs.nextInt();
            long[] A = new long[N];
            for (int i = 0; i < N; i++) {
                A[i] = fs.nextLong();
            }
            out.append(FactoryScheduler.solve(N, A)).append('\n');
        }

        System.out.print(out);
    }

    // -------------------------------------------------------------------------
    // Fast I/O — much faster than Scanner for large inputs
    // -------------------------------------------------------------------------
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        long nextLong() throws IOException {
            int c;
            do {
                c = read();
                if (c == -1) return Long.MIN_VALUE;
            } while (c <= ' ');

            long sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            long val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}
