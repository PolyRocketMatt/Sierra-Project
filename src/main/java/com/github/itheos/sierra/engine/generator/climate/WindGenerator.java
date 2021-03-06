package com.github.itheos.sierra.engine.generator.climate;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.math.noise.FBM;
import com.github.itheos.sierra.math.noise.SimplexNoise;
import com.github.itheos.sierra.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 18/03/2021.
 *
 * Generator that generates wind maps.
 */

public class WindGenerator extends ClimateGenerator {

    /**
     * Enum that contains all possible wind levels
     * for this generator.
     */
    public enum WindLevel {
        CALM("CALM", 0.0f),
        WINDY("WINDY", 0.4f),
        EXTREMELY_WINDY("EXTREMELY_WINDY", 0.8f);

        /** The key for a wind level.*/
        private String key;

        /** The corresponding noise value for this wind level. */
        private float level;

        /**
         * Initialize a new WindLevel.
         *
         * @param key the key
         * @param level the level
         */
        WindLevel(String key, float level) {
            this.key = key;
            this.level = level;
        }

        /**
         * Get the key representing this wind level.
         *
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * Get the level representing this wind level.
         *
         * @return the level
         */
        public float getLevel() {
            return level;
        }
    }

    /** Generator Variables. */
    private int directional, offset, octaves;
    private float scale, persistence, lacunarity, max;
    private float warp;

    /** Generator Utils. */
    private SimplexNoise noise, primaryX, primaryZ, directionalX, directionalZ;

    /**
     * Initialize a new WindGenerator.
     *
     * @param seed the seed for the generator
     * @param directional the directional seed for the generator
     * @param offset the offset used for this generator
     */
    public WindGenerator(int seed, int directional, int offset) {
        this.directional = MathUtils.fromDoubleSeed(seed, directional);
        this.offset = offset;
        this.octaves = Sierra.getGenerators().getAsInteger("generators.climate.wind.octaves");
        this.scale = Sierra.getGenerators().getAsFloat("generators.climate.wind.scale");
        this.persistence = Sierra.getGenerators().getAsFloat("generators.climate.wind.persistence");
        this.lacunarity = Sierra.getGenerators().getAsFloat("generators.climate.wind.lacunarity");
        this.warp = Sierra.getGenerators().getAsFloat("generators.climate.wind.warp");
        this.max = trueMax(octaves);

        noise = new SimplexNoise(seed);
        primaryX = new SimplexNoise(MathUtils.fromDoubleSeed(seed, 1));
        primaryZ = new SimplexNoise(MathUtils.fromDoubleSeed(seed, 2));
        directionalX = new SimplexNoise(directional);
        directionalZ = new SimplexNoise(directional);

        //  We do not save individual FBM's since this would kill performance
    }

    @Override
    public float noise(float x, float z) {
        //  Values between -1 and 1
        float qX = FBM.compute(primaryX, FBM.NoiseFilter.REGULAR, octaves, MathUtils.toFunction(scale), MathUtils.toFunction(persistence), MathUtils.toFunction(lacunarity),
                MathUtils.toFunction(1.0f), MathUtils.toFunction(1.0f), x + offset, z + offset);
        float qZ = FBM.compute(primaryZ, FBM.NoiseFilter.REGULAR, octaves, MathUtils.toFunction(scale), MathUtils.toFunction(persistence), MathUtils.toFunction(lacunarity),
                MathUtils.toFunction(1.0f), MathUtils.toFunction(1.0f), x + offset, z + offset);

        float rX = FBM.compute(directionalX, FBM.NoiseFilter.REGULAR, octaves, MathUtils.toFunction(scale), MathUtils.toFunction(persistence), MathUtils.toFunction(lacunarity),
                MathUtils.toFunction(1.0f), MathUtils.toFunction(1.0f), x + warp * qX + offset, z + warp * qZ + offset);
        float rZ = FBM.compute(directionalZ, FBM.NoiseFilter.REGULAR, octaves, MathUtils.toFunction(scale), MathUtils.toFunction(persistence), MathUtils.toFunction(lacunarity),
                MathUtils.toFunction(1.0f), MathUtils.toFunction(1.0f), x + warp * qX + offset, z + warp * qZ + offset);

        float value = FBM.compute(noise, FBM.NoiseFilter.REGULAR, octaves, MathUtils.toFunction(scale), MathUtils.toFunction(persistence), MathUtils.toFunction(lacunarity),
                MathUtils.toFunction(1.0f), MathUtils.toFunction(1.0f), x + warp * rX, z + warp * rZ);

        return MathUtils.normalize(value, -max, max);
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
}
