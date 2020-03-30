package com.github.gotterdemarung.hbench;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Contains sampling information
 */
public class Sampling {
    private final AtomicInteger index = new AtomicInteger(0);
    private final long[] values;
    private long average, min, max;
    private boolean sorted = false;

    public Sampling(final int capacity) {
        this.values = new long[capacity];
    }

    /**
     * Places value into samples collection
     *
     * @param value Value to add
     */
    public synchronized void add(final long value) {
        int idx = index.getAndIncrement();
        if (idx >= values.length) {
            return;
        }
        if (value < min || min == 0) {
            this.min = value;
        }
        if (value > max) {
            this.max = value;
        }
        this.average = (this.average * idx + value) / (idx + 1);
        this.sorted = false;
        this.values[idx] = value;
    }

    /**
     * @return Count of samples placed
     */
    public int getDone() {
        return index.get();
    }

    /**
     * @return Min sampled value
     */
    public long getMin() {
        return min;
    }

    /**
     * @return Max sampled value
     */
    public long getMax() {
        return max;
    }

    /**
     * @return Average sampled value
     */
    public float getAverage() {
        return average;
    }

    /**
     * @return True if sampling container can receive more data
     */
    public boolean isNotFull() {
        return index.get() < values.length;
    }

    /**
     * Sorts sampling data
     */
    public synchronized void sort() {
        Arrays.sort(values);
        this.sorted = true;
    }

    /**
     * Return mean value for given percentile
     *
     * @param percentile Percentile
     * @return Mean value
     */
    public long getMean(final int percentile) {
        if (!sorted) {
            throw new IllegalStateException("Not sorted");
        }
        int to = values.length * percentile / 100;
        long sum = 0;
        for (int i = 0; i < to; i++) {
            sum += values[i];
        }

        return sum / to;
    }
}
