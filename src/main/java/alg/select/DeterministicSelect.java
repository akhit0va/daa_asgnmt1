package alg.select;

import alg.metrics.Metrics;

import java.util.Arrays;

/**
 * Deterministic select (median of medians), k is 0-based index.
 */
public class DeterministicSelect {
    private final Metrics metrics;

    public DeterministicSelect(Metrics m) { this.metrics = m; }

    public int select(int[] a, int k) {
        if (a == null || k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        return selectRec(a, 0, a.length - 1, k);
    }

    private int selectRec(int[] a, int l, int r, int k) {
        metrics.enter();
        try {
            if (l == r) return a[l];
            int pivot = medianOfMedians(a, l, r);
            int pivotIndex = partitionAroundPivot(a, l, r, pivot);
            if (k == pivotIndex) return a[pivotIndex];
            else if (k < pivotIndex) return selectRec(a, l, pivotIndex - 1, k);
            else return selectRec(a, pivotIndex + 1, r, k);
        } finally {
            metrics.exit();
        }
    }

    private int partitionAroundPivot(int[] a, int l, int r, int pivotVal) {
        // find pivot and move to end
        int pivotIdx = l;
        boolean found = false;
        for (int i = l; i <= r; i++) {
            if (a[i] == pivotVal) { pivotIdx = i; found = true; break; }
        }
        if (found) {
            int t = a[pivotIdx]; a[pivotIdx] = a[r]; a[r] = t; metrics.swaps.incrementAndGet();
        }
        int store = l;
        for (int i = l; i < r; i++) {
            metrics.comparisons.incrementAndGet();
            if (a[i] < pivotVal) {
                int t = a[store]; a[store] = a[i]; a[i] = t; metrics.swaps.incrementAndGet();
                store++;
            }
        }
        int t = a[store]; a[store] = a[r]; a[r] = t; metrics.swaps.incrementAndGet();
        return store;
    }

    private int medianOfMedians(int[] a, int l, int r) {
        int n = r - l + 1;
        if (n <= 5) {
            Arrays.sort(a, l, r + 1);
            return a[l + n / 2];
        }
        int numMedians = (n + 4) / 5;
        for (int i = 0; i < numMedians; i++) {
            int subL = l + i * 5;
            int subR = Math.min(subL + 4, r);
            Arrays.sort(a, subL, subR + 1);
            int median = a[subL + (subR - subL) / 2];
            a[l + i] = median;
            metrics.allocations.incrementAndGet();
        }
        return medianOfMedians(a, l, l + numMedians - 1);
    }
}
