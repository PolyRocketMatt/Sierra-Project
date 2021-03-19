package com.github.itheos.sierra.engine.generator.climate;

import com.github.itheos.sierra.engine.generator.general.SimplexGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 19/03/2021.
 */

public class PrecipitationGenerator extends ClimateGenerator {

    /** Generator Utils. */
    private SimplexGenerator noise;

    /**
     * Initialize a new PrecipitationGenerator.
     *
     * @param seed the seed for the generator.
     */
    public PrecipitationGenerator(int seed) {
        this.noise = new SimplexGenerator(seed);
    }

    @Override
    public float noise(float x, float z) {
        return noise.noise(x, z);
    }

    @Override
    public float[][][] noise(@NotNull float[][] map, float bX, float bZ, int lX, int lZ) {
        return new float[lX][lZ][1];
    }

    @Override
    public float trueMax(int octaves) {
        return noise.trueMax(noise.getOctaves());
    }
}
