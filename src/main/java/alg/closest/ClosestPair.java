package alg.closest;

import alg.metrics.Metrics;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Closest Pair divide & conquer (O(n log n)).
 */
public class ClosestPair {
    private final Metrics metrics;

    public ClosestPair(Metrics m) { this.metrics = m; }

    public double closest(Point[] pts) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
        Point[] px = pts.clone();
        Point[] py = pts.clone();
        Arrays.sort(px, Comparator.comparingInt(p -> p.x));
        Arrays.sort(py, Comparator.comparingInt(p -> p.y));
        return rec(px, py);
    }

    private double rec(Point[] px, Point[] py) {
        metrics.enter();
        try {
            int n = px.length;
            if (n <= 3) return bruteForce(px);
            int mid = n / 2;
            Point midPoint = px[mid];

            Point[] lx = Arrays.copyOfRange(px, 0, mid);
            Point[] rx = Arrays.copyOfRange(px, mid, n);

            List<Point> ly = new ArrayList<>();
            List<Point> ry = new ArrayList<>();
            for (Point p : py) {
                if (p.x < midPoint.x || (p.x == midPoint.x && indexInArray(p, lx))) ly.add(p);
                else ry.add(p);
            }

            Point[] lya = ly.toArray(new Point[0]);
            Point[] rya = ry.toArray(new Point[0]);

            double dl = rec(lx, lya);
            double dr = rec(rx, rya);
            double d = Math.min(dl, dr);

            List<Point> strip = new ArrayList<>();
            for (Point p : py) if (Math.abs(p.x - midPoint.x) < d) strip.add(p);

            for (int i = 0; i < strip.size(); i++) {
                for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < d; j++) {
                    metrics.comparisons.incrementAndGet();
                    d = Math.min(d, dist(strip.get(i), strip.get(j)));
                }
            }
            return d;
        } finally {
            metrics.exit();
        }
    }

    private boolean indexInArray(Point p, Point[] a) {
        for (Point q : a) if (q.x == p.x && q.y == p.y) return true;
        return false;
    }

    private double bruteForce(Point[] a) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < a.length; i++) for (int j = i + 1; j < a.length; j++) {
            metrics.comparisons.incrementAndGet();
            best = Math.min(best, dist(a[i], a[j]));
        }
        return best;
    }

    private double dist(Point p1, Point p2) {
        long dx = (long) p1.x - p2.x;
        long dy = (long) p1.y - p2.y;
        return Math.hypot(dx, dy);
    }
}
