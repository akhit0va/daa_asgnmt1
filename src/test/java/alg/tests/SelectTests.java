package alg.tests;

import org.junit.jupiter.api.Test;
import alg.select.DeterministicSelect;
import alg.metrics.Metrics;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class SelectTests {
    @Test
    public void select_median_matches_sort() {
        Metrics m = new Metrics();
        DeterministicSelect ds = new DeterministicSelect(m);
        int[] a = new int[51];
        for (int i = 0; i < a.length; i++) a[i] = (int)(Math.random()*1000);
        int[] copy = a.clone();
        Arrays.sort(copy);
        int expected = copy[a.length/2];
        int got = ds.select(a, a.length/2);
        assertEquals(expected, got);
    }
}
