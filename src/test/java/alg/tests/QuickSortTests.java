package alg.tests;

import org.junit.jupiter.api.Test;
import alg.quicksort.QuickSort;
import alg.metrics.Metrics;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTests {
    @Test
    public void quicksort_correctness() {
        Metrics m = new Metrics();
        QuickSort qs = new QuickSort(m);
        int[] a = {5,3,2,8,1,4};
        qs.sort(a);
        assertArrayEquals(new int[]{1,2,3,4,5,8}, a);
    }
}
