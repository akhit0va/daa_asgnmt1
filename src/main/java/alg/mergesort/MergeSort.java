package alg.mergesort;

import alg.metrics.Metrics;

/**
 * MergeSort с reuse buffer и cutoff на insertion sort.
 */
public class MergeSort {
    private final Metrics metrics;
    private final int INSERTION_CUTOFF = 32;

    public MergeSort(Metrics m) { this.metrics = m; }

    public void sort(int[] a) {
        if (a == null || a.length <= 1) return;
        int[] buf = new int[a.length];
        sortRec(a, buf, 0, a.length);
    }

    private void sortRec(int[] a, int[] buf, int l, int r) {
        metrics.enter();
        try {
            int n = r - l;
            if (n <= 1) return;
            if (n <= INSERTION_CUTOFF) {
                insertion(a, l, r);
                return;
            }
            int m = (l + r) >>> 1;
            sortRec(a, buf, l, m);
            sortRec(a, buf, m, r);
            // merge a[l:m] and a[m:r] into buf[l:r]
            int i = l, j = m, k = l;
            while (i < m && j < r) {
                metrics.comparisons.incrementAndGet();
                if (a[i] <= a[j]) { buf[k++] = a[i++]; }
                else { buf[k++] = a[j++]; }
                metrics.allocations.incrementAndGet();
            }
            while (i < m) { buf[k++] = a[i++]; metrics.allocations.incrementAndGet(); }
            while (j < r) { buf[k++] = a[j++]; metrics.allocations.incrementAndGet(); }
            System.arraycopy(buf, l, a, l, n);
        } finally {
            metrics.exit();
        }
    }

    private void insertion(int[] a, int l, int r) {
        for (int i = l + 1; i < r; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= l) {
                metrics.comparisons.incrementAndGet();
                if (a[j] <= key) break;
                a[j + 1] = a[j];
                j--;
                metrics.allocations.incrementAndGet();
            }
            a[j + 1] = key;
            metrics.allocations.incrementAndGet();
        }
    }
}
