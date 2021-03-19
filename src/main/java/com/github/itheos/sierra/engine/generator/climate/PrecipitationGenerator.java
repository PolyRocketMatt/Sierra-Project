package com.github.itheos.sierra.engine.generator.climate;

import com.github.itheos.sierra.engine.generator.general.SimplexGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 19/03/2021.
 */

public class PrecipitationGenerator extends ClimateGenerator {

    /**
     * Enum that contains all possible temperature levels
     * for this generator.
     */
    public enum PrecipitationLevel {
        VERY_WET("VERY_WET", 0.0f),
        WET("WET", 0.2f),
        REGULAR_WET("REGULAR_WET", 0.4f),
        DRY("DRY", 0.6f),
        VERY_DRY("VERY_DRY", 0.8f);

        /** The key for a precipitation level.*/
        private String key;

        /** The corresponding noise value for this precipitation level. */
        private float level;

        /**
         * Initialize a new PrecipitationLevel.
         *
         * @param key the key
         * @param level the level
         */
        PrecipitationLevel(String key, float level) {
            this.key = key;
            this.level = level;
        }

        /**
         * Get the key representing this precipitation level.
         *
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * Get the level representing this precipitation level.
         *
         * @return the level
         */
        public float getLevel() {
            return level;
        }
    }

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
