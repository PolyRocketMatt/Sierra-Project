package com.github.itheos.sierra.engine.generator.climate;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.engine.biome.BiomeControlFactor;
import com.github.itheos.sierra.engine.biome.ControlFactors;
import com.github.itheos.sierra.engine.biome.LayeredController;
import com.github.itheos.sierra.math.noise.FBM;
import com.github.itheos.sierra.math.noise.SimplexNoise;
import com.github.itheos.sierra.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 18/03/2021.
 *
 * Generator that generates temperature maps.
 */

public class TemperatureGenerator extends ClimateGenerator {

    /** Generator Variables. */
    private int octaves;
    private float scale, persistence, lacunarity, max;
    private float lInfluence;

    /** Generator Utils. */
    private FBM fbm, distortionFbm;
    private SimplexNoise noise, distortion;

    /**
     * Initialize a new TemperatureGenerator.
     *
     * @param seed the seed for the generator
     */
    public TemperatureGenerator(int seed) {
        this.octaves = Sierra.getGenerators().getAsInteger("generators.climate.temperature.octaves");
        this.scale = Sierra.getGenerators().getAsFloat("generators.climate.temperature.scale");
        this.persistence = Sierra.getGenerators().getAsFloat("generators.climate.temperature.persistence");
        this.lacunarity = Sierra.getGenerators().getAsFloat("generators.climate.temperature.lacunarity");
        this.lInfluence = Sierra.getGenerators().getAsFloat("generators.climate.temperature.lacunarity-influence");
        this.max = trueMax(octaves);

        this.noise = new SimplexNoise(seed);
        this.fbm = new FBM(noise, FBM.NoiseFilter.REGULAR, octaves,
                MathUtils.toFunction(scale), MathUtils.toFunction(persistence),
                params -> lacunarity * lInfluence * MathUtils.sigmoid(params[0], 0.0f, -0.5f, 3.5f, 1.0f),
                MathUtils.toFunction(1.0f),
                MathUtils.toFunction(1.0f));
        this.distortion = new SimplexNoise(MathUtils.fromSeedVolatile(seed));
        this.distortionFbm = new FBM(distortion, FBM.NoiseFilter.REGULAR, octaves,
                MathUtils.toFunction(scale), MathUtils.toFunction(persistence),
                params -> lacunarity * lInfluence * MathUtils.sigmoid(params[0], 0.0f, -0.5f, 3.5f, 1.0f),
                params -> 0.5f * params[0],
                MathUtils.toFunction(1.0f));
    }

    @Override
    public float noise(float x, float z) {
        return (MathUtils.normalize(-2.0f * max, max, fbm.compute(x, z) + distortionFbm.compute(x, z)) / 2.0f);
    }

    @Override
    public float[][][] noise(@NotNull float[][] map, float bX, float bZ, int lX, int lZ) {
        return new float[lX][lZ][1];
    }

    @Override
    public float trueMax(int octaves) {
        float max = 0.0f;
        float amp = 1.0f;
        for (int i = 0; i < octaves; i++) {
            max += 1.0f * amp;
            amp *= persistence;
        }

        return max;
    }

    @Override
    public BiomeControlFactor translate(float x, float z) {
        return ControlFactors.TemperatureLevel.translate(noise(x, z));
    }
}
