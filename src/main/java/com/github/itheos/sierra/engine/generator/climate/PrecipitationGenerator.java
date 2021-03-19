package com.github.itheos.sierra.engine.generator.climate;

import com.github.itheos.sierra.engine.biome.BiomeControlFactor;
import com.github.itheos.sierra.engine.biome.ControlFactors;
import com.github.itheos.sierra.engine.generator.general.SimplexGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 19/03/2021.
 */

public class PrecipitationGenerator extends ClimateGenerator {

    /** Generator Variables. */
    private float max;

    /** Generator Utils. */
    private SimplexGenerator noise;

    /**
     * Initialize a new PrecipitationGenerator.
     *
     * @param seed the seed for the generator.
     */
    public PrecipitationGenerator(int seed) {
        this.noise = new SimplexGenerator(seed);
        this.max = noise.trueMax(noise.getOctaves());
    }

    @Override
    public float noise(float x, float z) {
        return noise.noise(x, z) / max;
    }

    @Override
    public float[][][] noise(@NotNull float[][] map, float bX, float bZ, int lX, int lZ) {
        return new float[lX][lZ][1];
    }

    @Override
    public float trueMax(int octaves) {
        return noise.trueMax(noise.getOctaves());
    }

    @Override
    public BiomeControlFactor translate(float x, float z) {
        return ControlFactors.PrecipitationLevel.translate(noise(x, z));
    }
}
