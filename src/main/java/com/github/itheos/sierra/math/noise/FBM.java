package com.github.itheos.sierra.math.noise;

import com.github.itheos.sierra.math.function.Function;
import com.github.itheos.sierra.utils.MathUtils;

/**
 * Created by PolyRocketMatt on 13/03/2021.
 *
 * This class provides a fractional brownian
 * motion for the given parameters. These parameters
 * include octaves, scale, persistence, lacunarity,
 * amplitude and frequency as well as a the type
 * of noise and filter for the noise.
 */

public class FBM {

    /**
     * Noise filters can transform noise values.
     */
    public enum NoiseFilter {
        REGULAR(params -> params[0]),
        BILLOWY(params -> MathUtils.abs(params[0])),
        RIGID(params -> 1.0f - MathUtils.abs(params[0]));

        Function<Float> function;

        NoiseFilter(Function<Float> function) {
            this.function = function;
        }

        public Function<Float> getFunction() {
            return function;
        }
    }

    private int octaves;

    private Noise noise;
    private NoiseFilter filter;
    private Function<Float> scale, persistence, lacunarity;
    private Function<Float> amplitude, frequency;

    /**
     * Initialize a new FBM (Fractional Brownian Motion) given
     * a set of rules.
     *
     * @param noise the noise algorithm to be used
     * @param filter the filter algorithm for the noise
     * @param octaves the amount of octaves
     * @param scale the scale function
     * @param persistence the persistence function
     * @param lacunarity the lacunarity function
     * @param amplitude the amplitude function
     * @param frequency the frequency function
     */
    public FBM(Noise noise, NoiseFilter filter, int octaves, Function<Float> scale, Function<Float> persistence, Function<Float> lacunarity,
               Function<Float> amplitude, Function<Float> frequency) {
        this.octaves = octaves;

        this.noise = noise;
        this.filter = filter;
        this.scale = scale;
        this.persistence = persistence;
        this.lacunarity = lacunarity;
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    /**
     * Compute filtered FBM for coordinates x and z
     * with the given parameters.
     *
     * @param noise the type of noise
     * @param filter the filter of noise
     * @param octaves the amount of octaves
     * @param scale the scale of the noise
     * @param persistence the persistence
     * @param lacunarity the lacunarity
     * @param amplitude the amplitude
     * @param frequency the frequency
     * @param x the x coordinate
     * @param z the z coordinate
     * @return the noise value computed with FBM
     */
    public static float compute(Noise noise, NoiseFilter filter, int octaves, Function<Float> scale, Function<Float> persistence, Function<Float> lacunarity,
                                Function<Float> amplitude, Function<Float> frequency, float x, float z) {
        float amp = amplitude.call();
        float freq = frequency.call();
        float value = 0.0f;

        for (int i = 0; i < octaves; i++) {
            float sc = scale.call(value);
            float sX = x / sc * freq;
            float sZ = z / sc * freq;

            value += filter.function.call(noise.evaluate(sX, sZ)[0]) * amp;
            amp *= persistence.call(value);
            freq *= lacunarity.call(value);
        }

        return value;
    }

    /**
     * Compute filtered FBM for coordinates x and z.
     *
     * @param x the x coordinate
     * @param z the z coordinate
     * @return the noise value computed with FBM
     */
    public float compute(float x, float z) {
        float amp = amplitude.call();
        float freq = frequency.call();
        float value = 0.0f;

        for (int i = 0; i < octaves; i++) {
            float sc = scale.call(value);
            float sX = x / sc * freq;
            float sZ = z / sc * freq;

            value += filter.function.call(noise.evaluate(sX, sZ)[0]) * amp;
            amp *= persistence.call(value);
            freq *= lacunarity.call(value);
        }

        return value;
    }

    /**
     * Pre-Compute filtered FBM for an lX x lZ given
     * array of coordinates.
     *
     * @param bX the base x coordinate of the region
     * @param bZ the base z coordinate of the region
     * @param lX the length of the region
     * @param lZ the depth of the region
     * @return an array of lX x lZ noise values computed with FBM
     */
    public float[][] preCompute(float bX, float bZ, int lX, int lZ) {
        float[][] preComputation = new float[lX][lZ];

        for (int x = 0; x < lX; x++)
            for (int z = 0; z < lZ; z++)
                preComputation[x][z] = compute(bX + x, bZ + z);

        return preComputation;
    }
}
