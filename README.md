<<<<<<< HEAD
# Design and Analysis of Algorithms – Assignment

## Learning Goals
- Practice divide-and-conquer algorithms with recursion in Java.
- Measure time, recursion depth, and comparisons.
- Understand Master Theorem (three cases) and Akra–Bazzi intuition.
- Compare theory with experimental results.

---

## Implemented Algorithms

### 1. MergeSort
- Classic divide and conquer with linear merge.
- Added reusable buffer to avoid many allocations.
- Small cutoff (Insertion Sort for very small arrays).
- **Recurrence:** T(n) = 2T(n/2) + Θ(n).  
  → Master Theorem, Case 2 → **Θ(n log n)**.

### 2. QuickSort
- Randomized pivot selection.
- Always recurse on the smaller partition, iterate on the larger.
- This keeps recursion depth ≈ O(log n).
- **Average recurrence:** T(n) = T(k) + T(n−k−1) + Θ(n).  
  → Expected **Θ(n log n)**.
- Worst case O(n²) but randomization makes it very rare.

### 3. Deterministic Select (Median of Medians)
- Groups of 5, pick median of medians as pivot.
- Partition in-place.
- Recurse only on one side (the one containing the answer).
- Recurrence roughly: T(n) ≤ T(n/5) + T(7n/10) + Θ(n).  
  → By Akra–Bazzi → **Θ(n)**.

### 4. Closest Pair of Points (2D)
- Sort points by x.
- Divide into halves, solve recursively.
- “Strip” step: only check ≤ 7–8 neighbors in sorted y-order.
- **Recurrence:** T(n) = 2T(n/2) + Θ(n).  
  → Master Theorem, Case 2 → **Θ(n log n)**.

---

## Architecture Notes
- `metrics/Metrics` counts comparisons, swaps, recursion depth, and time.
- Recursion depth is tracked with a simple global counter.
- For QuickSort, recursion is always on smaller part → stack bounded.
- `ArraysUtil` has helpers for partition, swap, shuffle, etc.
- `Main` class runs all algorithms and writes results to `metrics.csv`.

---

## Experiments
- Measured time for different `n` (array sizes).
- Compared recursion depth between MergeSort and QuickSort.
- For Select, checked against `Arrays.sort(a)[k]` on 100 random arrays.
- Closest Pair compared with O(n²) algorithm on small inputs.

---

## Results and Discussion
- Sorting (MergeSort, QuickSort) grew like **n log n** as expected.
- Select was linear, but with bigger constant factors.
- Closest Pair behaved as **n log n**, but strip step was fast in practice.
- Depth of QuickSort was always close to `2*log2(n)` as theory predicts.
- Small constant factors matter:
   - cache effects,
   - garbage collection in Java,
   - insertion sort cutoff improved MergeSort speed.

---

## Git Workflow
- `main` branch for stable releases (`v0.1`, `v1.0`).
- Feature branches:
   - `feature/mergesort`, `feature/quicksort`, `feature/select`, `feature/closest`, `feature/metrics`.
- Commit examples:
   - `init: maven, junit5, readme`
   - `feat(mergesort): baseline + cutoff + tests`
   - `feat(quicksort): randomized pivot + depth guard`
   - `feat(select): median of medians`
   - `feat(closest): divide and conquer`
   - `docs(report): analysis and plots`

---

## Conclusion
The theoretical analysis (Master Theorem, Akra–Bazzi) matched the measured results.  
Minor differences came from constant factors and JVM details.  
Overall, the assignment helped me understand how to balance **theory** with **real-world performance**.
=======
# daa_asgnmt1
>>>>>>> 45449f89db1391081641e885017ae1b194fcd78a
