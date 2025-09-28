package alg.metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Metrics: простая структура для подсчёта метрик выполнения алгоритмов.
 */
public class Metrics {
    public final AtomicLong comparisons = new AtomicLong();
    public final AtomicLong swaps = new AtomicLong();
    public final AtomicLong allocations = new AtomicLong();

    private final ThreadLocal<Integer> depth = ThreadLocal.withInitial(() -> 0);
    private final AtomicLong maxDepth = new AtomicLong(0);

    public void enter() {
        int d = depth.get() + 1;
        depth.set(d);
        maxDepth.updateAndGet(x -> Math.max(x, d));
    }

    public void exit() {
        depth.set(depth.get() - 1);
    }

    public int getMaxDepth() {
        return (int) maxDepth.get();
    }

    public void reset() {
        comparisons.set(0); swaps.set(0); allocations.set(0);
        depth.set(0); maxDepth.set(0);
    }

    public String toCsvLine(String algo, long n, long timeNs) {
        return algo + "," + n + "," + timeNs + "," + getMaxDepth() + "," + comparisons.get() + "," + swaps.get() + "," + allocations.get();
    }

    public static void writeHeader(FileWriter w) throws IOException {
        w.write("algo,n,time_ns,max_depth,comparisons,swaps,allocations\n");
    }
}
