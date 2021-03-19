package com.github.itheos.sierra.engine.biome;

import com.github.itheos.sierra.math.Range;

/**
 * Created by PolyRocketMatt on 19/03/2021.
 */

public class ControlFactors {

    /**
     * Enum that contains all possible temperature levels
     * for this generator.
     */
    public enum TemperatureLevel implements BiomeControlFactor {
        FREEZING("FREEZING", Range.of(0.0f, 0.2f)),
        COLD("COLD", Range.of(0.2f, 0.4f)),
        LUKEWARM("LUKEWARM", Range.of(0.4f, 0.6f)),
        HOT("HOT", Range.of(0.6f, 0.8f)),
        VERY_HOT("VERY_HOT", Range.of(0.8f, 1.0f));

        /** The key for a temperature level.*/
        private String key;

        /** The corresponding noise value for this temperature level. */
        private Range level;

        /**
         * Initialize a new TemperatureLevel.
         *
         * @param key the key
         * @param level the level
         */
        TemperatureLevel(String key, Range level) {
            this.key = key;
            this.level = level;
        }

        /**
         * Get the key representing this temperature level.
         *
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * Get the level representing this temperature level.
         *
         * @return the level
         */
        public Range getLevel() {
            return level;
        }

        @Override
        public BiomeControlFactor get(String key) {
            for (TemperatureLevel level : values())
                if (level.key.equalsIgnoreCase(key))
                    return level;
            return null;
        }

        @Override
        public BiomeControlFactor translate(float value) {
            for (TemperatureLevel level : values())
                if (level.level.isInRange(value))
                    return level;
            return null;
        }
    }

    /**
     * Enum that contains all possible temperature levels
     * for this generator.
     */
    public enum PrecipitationLevel implements BiomeControlFactor {
        VERY_WET("VERY_WET", Range.of(0.0f, 0.2f)),
        WET("WET", Range.of(0.2f, 0.4f)),
        REGULAR_WET("REGULAR_WET", Range.of(0.4f, 0.6f)),
        DRY("DRY", Range.of(0.6f, 0.8f)),
        VERY_DRY("VERY_DRY", Range.of(0.8f, 1.0f));

        /** The key for a precipitation level.*/
        private String key;

        /** The corresponding noise value for this precipitation level. */
        private Range level;

        /**
         * Initialize a new PrecipitationLevel.
         *
         * @param key the key
         * @param level the level
         */
        PrecipitationLevel(String key, Range level) {
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
        public Range getLevel() {
            return level;
        }

        @Override
        public BiomeControlFactor get(String key) {
            for (PrecipitationLevel level : values())
                if (level.key.equalsIgnoreCase(key))
                    return level;
            return null;
        }

        @Override
        public BiomeControlFactor translate(float value) {
            for (PrecipitationLevel level : values())
                if (level.level.isInRange(value))
                    return level;
            return null;
        }
    }

    /**
     * Enum that contains all possible wind levels
     * for this generator.
     */
    public enum WindLevel implements BiomeControlFactor {
        CALM("CALM", Range.of(0.0f, 0.4f)),
        WINDY("WINDY", Range.of(0.4f, 0.8f)),
        EXTREMELY_WINDY("EXTREMELY_WINDY", Range.of(0.8f, 1.0f));

        /** The key for a wind level.*/
        private String key;

        /** The corresponding noise value for this wind level. */
        private Range level;

        /**
         * Initialize a new WindLevel.
         *
         * @param key the key
         * @param level the level
         */
        WindLevel(String key, Range level) {
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
        public Range getLevel() {
            return level;
        }

        @Override
        public BiomeControlFactor get(String key) {
            for (WindLevel level : values())
                if (level.key.equalsIgnoreCase(key))
                    return level;
            return null;
        }

        @Override
        public BiomeControlFactor translate(float value) {
            for (WindLevel level : values())
                if (level.level.isInRange(value))
                    return level;
            return null;
        }
    }

    /**
     * Enum that contains all possible topography levels
     * for this generator.
     */
    public enum TopographyLevel implements BiomeControlFactor {
        FLAT("FLAT", Range.of(0.0f, 0.2f)),
        HILLY("HILLY", Range.of(0.2f, 0.4f)),
        HILLS("HILLS", Range.of(0.4f, 0.6f)),
        EXTREME_HILLS("EXTREME_HILLS", Range.of(0.6f, 0.8f)),
        MOUNTAINOUS("MOUNTAINOUS", Range.of(0.8f, 1.0f));

        /** The key for a steepness level.*/
        private String key;

        /** The corresponding noise value for this steepness level. */
        private Range level;

        /**
         * Initialize a new TopographyLevel.
         *
         * @param key the key
         * @param level the level
         */
        TopographyLevel(String key, Range level) {
            this.key = key;
            this.level = level;
        }

        /**
         * Get the key representing this steepness level.
         *
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * Get the level representing this steepness level.
         *
         * @return the level
         */
        public Range getLevel() {
            return level;
        }

        @Override
        public BiomeControlFactor get(String key) {
            for (TopographyLevel level : values())
                if (level.key.equalsIgnoreCase(key))
                    return level;
            return null;
        }

        @Override
        public BiomeControlFactor translate(float value) {
            for (TopographyLevel level : values())
                if (level.level.isInRange(value))
                    return level;
            return null;
        }
    }

    /**
     * Enum that contains all possible wetness levels
     * for this generator.
     */
    public enum WetnessLevel implements BiomeControlFactor {
        DRY("DRT", Range.of(0.0f, 0.3f)),
        WET("WET", Range.of(0.3f, 0.7f)),
        EXTREMELY_WET("EXTREMELY_WET", Range.of(0.7f, 1.0f));

        /** The key for a wetness level.*/
        private String key;

        /** The corresponding noise value for this wetness level. */
        private Range level;

        /**
         * Initialize a new WetnessLevel.
         *
         * @param key the key
         * @param level the level
         */
        WetnessLevel(String key, Range level) {
            this.key = key;
            this.level = level;
        }

        /**
         * Get the key representing this wetness level.
         *
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * Get the level representing this wetness level.
         *
         * @return the level
         */
        public Range getLevel() {
            return level;
        }

        @Override
        public BiomeControlFactor get(String key) {
            for (WetnessLevel level : values())
                if (level.key.equalsIgnoreCase(key))
                    return level;
            return null;
        }

        @Override
        public BiomeControlFactor translate(float value) {
            for (WetnessLevel level : values())
                if (level.level.isInRange(value))
                    return level;
            return null;
        }
    }

    /**
     * Enum that contains all possible humidity levels
     * for this generator.
     */
    public enum HumidityLevel implements BiomeControlFactor {
        ARID("ARID", Range.of(0.0f, 0.5f)),
        HUMID("HUMID", Range.of(0.0f, 0.8f)),
        VERY_HUMID("VERY_HUMID", Range.of(0.8f, 1.0f));

        /** The key for a temperature level.*/
        private String key;

        /** The corresponding noise value for this humidity level. */
        private Range level;

        /**
         * Initialize a new HumidityLevel.
         *
         * @param key the key
         * @param level the level
         */
        HumidityLevel(String key, Range level) {
            this.key = key;
            this.level = level;
        }

        /**
         * Get the key representing this humidity level.
         *
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * Get the level representing this humidity level.
         *
         * @return the level
         */
        public Range getLevel() {
            return level;
        }

        @Override
        public BiomeControlFactor get(String key) {
            for (HumidityLevel level : values())
                if (level.key.equalsIgnoreCase(key))
                    return level;
            return null;
        }

        @Override
        public BiomeControlFactor translate(float value) {
            for (HumidityLevel level : values())
                if (level.level.isInRange(value))
                    return level;
            return null;
        }
    }

    /**
     * Enum that contains all possible vegetation levels
     * for this generator.
     */
    public enum VegetationLevel implements BiomeControlFactor {
        BARE("BARE", Range.of(0.0f, 0.2f)),
        SOME("SOME", Range.of(0.2f, 0.4f)),
        MEDIUM("MEDIUM", Range.of(0.4f, 0.6f)),
        LOTS("LOTS", Range.of(0.6f, 0.8f)),
        OVERGROWN("VERY_HOT", Range.of(0.8f, 1.0f));

        /** The key for a vegetation level.*/
        private String key;

        /** The corresponding noise value for this vegetation level. */
        private Range level;

        /**
         * Initialize a new VegetationLevel.
         *
         * @param key the key
         * @param level the level
         */
        VegetationLevel(String key, Range level) {
            this.key = key;
            this.level = level;
        }

        /**
         * Get the key representing this vegetation level.
         *
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * Get the level representing this vegetation level.
         *
         * @return the level
         */
        public Range getLevel() {
            return level;
        }

        @Override
        public BiomeControlFactor get(String key) {
            for (VegetationLevel level : values())
                if (level.key.equalsIgnoreCase(key))
                    return level;
            return null;
        }

        @Override
        public BiomeControlFactor translate(float value) {
            for (VegetationLevel level : values())
                if (level.level.isInRange(value))
                    return level;
            return null;
        }
    }

}
