package alg.cli;

import alg.metrics.Metrics;
import alg.mergesort.MergeSort;
import alg.quicksort.QuickSort;
import alg.select.DeterministicSelect;
import alg.closest.ClosestPair;

import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * CLI: запускает бенчмарки и пишет metrics.csv
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Metrics metrics = new Metrics();
        int[] sizes = new int[] {1000, 2000, 5000, 10000};
        Random rnd = new Random(42);

        try (FileWriter w = new FileWriter("metrics.csv")) {
            Metrics.writeHeader(w);
            for (int n : sizes) {
                System.out.println("Running n=" + n);
                int[] base = new int[n];
                for (int i = 0; i < n; i++) base[i] = rnd.nextInt();

                // MergeSort
                int[] a1 = base.clone();
                metrics.reset();
                MergeSort ms = new MergeSort(metrics);
                long t0 = System.nanoTime();
                ms.sort(a1);
                long t1 = System.nanoTime();
                w.write(metrics.toCsvLine("MergeSort", n, t1 - t0) + "\n");

                // QuickSort
                int[] a2 = base.clone();
                metrics.reset();
                QuickSort qs = new QuickSort(metrics);
                t0 = System.nanoTime();
                qs.sort(a2);
                t1 = System.nanoTime();
                w.write(metrics.toCsvLine("QuickSort", n, t1 - t0) + "\n");

                // Deterministic Select
                int[] a3 = base.clone();
                metrics.reset();
                DeterministicSelect ds = new DeterministicSelect(metrics);
                t0 = System.nanoTime();
                int median = ds.select(a3, n/2);
                t1 = System.nanoTime();
                w.write(metrics.toCsvLine("DeterministicSelect", n, t1 - t0) + "\n");

                // Closest Pair
                java.awt.Point[] pts = new java.awt.Point[n];
                for (int i = 0; i < n; i++) pts[i] = new Point(rnd.nextInt(1000000), rnd.nextInt(1000000));
                metrics.reset();
                ClosestPair cp = new ClosestPair(metrics);
                t0 = System.nanoTime();
                double d = cp.closest(pts);
                t1 = System.nanoTime();
                w.write(metrics.toCsvLine("ClosestPair", n, t1 - t0) + "\n");

                w.flush();
            }
        }
        System.out.println("metrics.csv written");
    }
}
