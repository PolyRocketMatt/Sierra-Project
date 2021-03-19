package com.github.itheos.sierra.math;

/**
 * Created by PolyRocketMatt on 18/03/2021.
 *
 * A range defines a set of real numbers
 * between two floating point numbers.
 */

public class Range {

    /** The min and max boundaries. */
    private float min, max;

    /**
     * Initialize a new Range.
     *
     * @param min the minimum boundary
     * @param max the maximum boundary
     */
    public Range(float min, float max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Check to see if a value is within range of the boundaries.
     *
     * @param x the value to be checked
     * @return true if the value is within the range of the boundaries
     */
    public boolean isInRange(float x) {
        return !(x < min) && !(x > max);
    }

    /**
     * Create a new range.
     *
     * @param min the minimum boundary
     * @param max the maximum boundary
     * @return a new Range constructed with the given boundaries
     */
    public static Range of(float min, float max) {
        return new Range(min, max);
    }

}
