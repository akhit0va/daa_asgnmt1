package alg.quicksort;

import alg.metrics.Metrics;
import alg.util.ArraysUtil;

import java.util.Random;

/**
 * Randomized QuickSort with recurse-on-smaller to bound stack depth.
 */
public class QuickSort {
    private final Metrics metrics;
    private final Random rnd = new Random(12345);

    public QuickSort(Metrics m) { this.metrics = m; }

    public void sort(int[] a) {
        if (a == null || a.length <= 1) return;
        ArraysUtil.shuffle(a);
        quickSortLoop(a, 0, a.length - 1);
    }

    private void quickSortLoop(int[] a, int l0, int r0) {
        int l = l0, r = r0;
        while (l < r) {
            metrics.enter();
            try {
                int p = partition(a, l, r);
                int leftSize = p - l;
                int rightSize = r - p;
                if (leftSize < rightSize) {
                    quickSortLoop(a, l, p - 1);
                    l = p + 1;
                } else {
                    quickSortLoop(a, p + 1, r);
                    r = p - 1;
                }
            } finally {
                metrics.exit();
            }
        }
    }

    private int partition(int[] a, int l, int r) {
        int pivotIndex = l + rnd.nextInt(r - l + 1);
        int pivot = a[pivotIndex];
        // move pivot to end
        int t = a[pivotIndex]; a[pivotIndex] = a[r]; a[r] = t; metrics.swaps.incrementAndGet();
        int store = l;
        for (int i = l; i < r; i++) {
            metrics.comparisons.incrementAndGet();
            if (a[i] <= pivot) {
                t = a[store]; a[store] = a[i]; a[i] = t; metrics.swaps.incrementAndGet();
                store++;
            }
        }
        // move pivot to its final place
        t = a[store]; a[store] = a[r]; a[r] = t; metrics.swaps.incrementAndGet();
        return store;
    }
}
