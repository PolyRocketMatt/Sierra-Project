package com.github.itheos.sierra.math;

/**
 * Created by PolyRocketMatt on 18/03/2021.
 */

public class Range {

    private float min, max;

    public Range(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public boolean isInRange(float x) {
        return !(x < min) && !(x > max);
    }

    public static Range of(float min, float max) {
        return new Range(min, max);
    }

}
