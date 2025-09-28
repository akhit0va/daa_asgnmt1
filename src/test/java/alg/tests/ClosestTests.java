package alg.tests;

import org.junit.jupiter.api.Test;
import alg.closest.ClosestPair;
import alg.metrics.Metrics;
import java.awt.Point;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestTests {
    @Test
    public void closest_matches_bruteforce_small() {
        Metrics m = new Metrics();
        ClosestPair cp = new ClosestPair(m);
        Point[] pts = new Point[] { new Point(0,0), new Point(3,4), new Point(1,1), new Point(6,6) };
        double got = cp.closest(pts);
        assertEquals(Math.hypot(1,1), got, 1e-9);
    }
}
