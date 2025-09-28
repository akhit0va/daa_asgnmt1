package alg.tests;

import org.junit.jupiter.api.Test;
import alg.mergesort.MergeSort;
import alg.metrics.Metrics;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTests {
    @Test
    public void mergesort_correctness() {
        Metrics m = new Metrics();
        MergeSort ms = new MergeSort(m);
        int[] a = {5,3,2,8,1,4};
        ms.sort(a);
        assertArrayEquals(new int[]{1,2,3,4,5,8}, a);
    }
}
