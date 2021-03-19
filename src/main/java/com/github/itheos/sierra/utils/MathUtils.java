package com.github.itheos.sierra.utils;

import com.github.itheos.sierra.math.function.Function;

import java.util.Random;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 *
 * Utilities regarding maths.
 */

public class MathUtils {

    private static final float E = 2.7182818284590452354f;
    private static final float PI = 3.14159265358979323846f;
    private static final Random random = new Random();

    public static int intSeed() {
        return random.nextInt(2 << 28);
    }

    public static int offset(int max) { return random.nextInt(max); }

    public static int fromSeedVolatile(int seed) { return seed % (seed / 10) * (seed % 2); }

    public static int fromDoubleSeed(int s1, int s2) { return (s1 + s2) / (s1 % s2); }

    /**
     * SmoothStep function on value t using fifth-degree polynomial.
     *
     * @param t input value
     * @return SmoothStep value for input
     */
    public static float smoothStep(float t) {
        return t * t * t * (t * (t * 6.0f - 15.0f) + 10.0f);
    }

    /**
     * Derivation of the SmoothStep function on value t using
     * derivative of fifth-degree polynomial.
     *
     * @param t input value
     * @return Derived SmoothStep value for input
     */
    public static float smoothStepDerivative(float t) {
        return t * t * (t * (t * 30.0f - 60.0f) + 30.0f);
    }

    /**
     * Linear interpolation for value t between min and max.
     *
     * @param min the minimum boundary
     * @param max the maximum boundary
     * @param t input value
     * @return linearly interpolated value for input
     */
    public static float lerp(float min, float max, float t) {
        return min + t * (max - min);
    }

    /**
     * Normalize the value t to be within [0;1]
     *
     * @param min the minimum value of t
     * @param max the maximum value of t
     * @param t input value
     * @return value between [0;1]
     */
    public static float normalize(float min, float max, float t) {
        return (t - min) / (max - min);
    }

    /**
     * Normalize bi-directional, this means the maximum value is
     * reached when the value to normalize is in the middle between
     * min and max.
     *
     * @param min the minimum value of t
     * @param max the maximum value of t
     * @param t input value
     * @return value between [0;1]
     */
    public static float biDirectionalNormalize(float min, float max, float t) {
        float middle = (min + max) / 2;
        return (t >= middle) ? normalize(middle, max, t) : normalize(min, middle, t);
    }

    /**
     * Quadratic curve for value t.
     *
     * @param convex convex or concave quadratic
     * @param offsetX offset in x direction
     * @param offsetY offset in y direction
     * @param stretchX stretch in x direction
     * @param t input value
     * @return quadratic value for t
     */
    public static float quadratic(boolean convex, float offsetX, float offsetY, float stretchX, float t) {
        return offsetY + (convex ? -1.0f : 1.0f) * stretchX * (float) Math.pow(t + offsetX, 2.0f);
    }

    /**
     * Calculate the absolute value for t.
     *
     * @param t input value
     * @return the absolute value of t
     */
    public static float abs(float t) { return (t >= 0) ? t : -t; }

    /**
     * Sigmoid curve for value t.
     *
     * @param offsetX offset in x direction
     * @param offsetY offset in y direction
     * @param stretchX stretch in x direction
     * @param stretchY stretch in y direction
     * @param t input value
     * @return sigmoid value for input
     */
    public static float sigmoid(float offsetX, float offsetY, float stretchX, float stretchY, float t) {
        return offsetY + stretchY * (1.0f / (1.0f + (float) Math.pow(E, (-stretchX * (t + offsetX)))));
    }

    /**
     * Gompertz curve for value t.
     *
     * @param asymptote the upper asymptote
     * @param offsetX the offset in x direction
     * @param yScaling the growth rate in the y direction
     * @param factor an offset factor
     * @param t input value
     * @return gompertz value for input
     */
    public static float gompertz(float asymptote, float offsetX, float yScaling, float factor, float t) {
        float cExp = -yScaling * (t + factor);
        float bExp = -offsetX * (float) Math.pow(E, cExp);

        return asymptote * (float) Math.pow(E, bExp);
    }

    /**
     * Calculate the square root of a given value.
     *
     * @param value input value
     * @return the square root
     */
    public static float sqrt(float value) {
        if (value < 0.0f)
            return 0.0f;

        if(value == 0)
            return 0;

        float t;
        float sqrt = value / 2;

        do {
            t = sqrt;
            sqrt = (t + (value / t)) / 2;
        } while ((t - sqrt) != 0);

        return sqrt;
    }

    /**
     * Return a new function for a constant value t
     *
     * @param t input value
     * @return function that returns t
     */
    public static Function<Float> toFunction(float t) {
        return params -> t;
    }



}