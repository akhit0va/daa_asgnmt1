package alg.util;

import java.util.Random;

public final class ArraysUtil {
    private static final Random R = new Random(123456);

    private ArraysUtil() {}

    public static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    public static void shuffle(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = R.nextInt(i + 1);
            swap(a, i, j);
        }
    }

    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i-1] > a[i]) return false;
        return true;
    }
}
