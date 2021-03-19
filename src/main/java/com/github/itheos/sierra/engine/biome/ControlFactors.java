package com.github.itheos.sierra.engine.biome;

/**
 * Created by PolyRocketMatt on 19/03/2021.
 */

public class ControlFactors {

    /**
     * Enum that contains all possible temperature levels
     * for this generator.
     */
    public enum TemperatureLevel implements BiomeControlFactor {
        FREEZING("FREEZING", 0.0f),
        COLD("COLD", 0.2f),
        LUKEWARM("LUKEWARM", 0.4f),
        HOT("HOT", 0.6f),
        VERY_HOT("VERY_HOT", 0.8f);

        /** The key for a temperature level.*/
        private String key;

        /** The corresponding noise value for this temperature level. */
        private float level;

        /**
         * Initialize a new TemperatureLevel.
         *
         * @param key the key
         * @param level the level
         */
        TemperatureLevel(String key, float level) {
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
        public float getLevel() {
            return level;
        }

        @Override
        public BiomeControlFactor get(String key) {
            for (TemperatureLevel level : values())
                if (level.key.equalsIgnoreCase(key))
                    return level;
            return null;
        }
    }

    /**
     * Enum that contains all possible temperature levels
     * for this generator.
     */
    public enum PrecipitationLevel implements BiomeControlFactor {
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

        @Override
        public BiomeControlFactor get(String key) {
            for (PrecipitationLevel level : values())
                if (level.key.equalsIgnoreCase(key))
                    return level;
            return null;
        }
    }

    /**
     * Enum that contains all possible wind levels
     * for this generator.
     */
    public enum WindLevel implements BiomeControlFactor {
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

        @Override
        public BiomeControlFactor get(String key) {
            for (WindLevel level : values())
                if (level.key.equalsIgnoreCase(key))
                    return level;
            return null;
        }
    }

    /**
     * Enum that contains all possible topography levels
     * for this generator.
     */
    public enum TopographyLevel implements BiomeControlFactor {
        FLAT("FLAT", 0.0f),
        HILLY("HILLY", 0.2f),
        HILLS("HILLS", 0.4f),
        EXTREME_HILLS("EXTREME_HILLS", 0.6f),
        MOUNTAINOUS("MOUNTAINOUS", 0.8f);

        /** The key for a steepness level.*/
        private String key;

        /** The corresponding noise value for this steepness level. */
        private float level;

        /**
         * Initialize a new TopographyLevel.
         *
         * @param key the key
         * @param level the level
         */
        TopographyLevel(String key, float level) {
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
        public float getLevel() {
            return level;
        }

        @Override
        public BiomeControlFactor get(String key) {
            for (TopographyLevel level : values())
                if (level.key.equalsIgnoreCase(key))
                    return level;
            return null;
        }
    }

    /**
     * Enum that contains all possible wetness levels
     * for this generator.
     */
    public enum WetnessLevel implements BiomeControlFactor {
        DRY("DRT", 0.0f),
        WET("WET", 0.3f),
        EXTREMELY_WET("EXTREMELY_WET", 0.7f);

        /** The key for a wetness level.*/
        private String key;

        /** The corresponding noise value for this wetness level. */
        private float level;

        /**
         * Initialize a new WetnessLevel.
         *
         * @param key the key
         * @param level the level
         */
        WetnessLevel(String key, float level) {
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
        public float getLevel() {
            return level;
        }

        @Override
        public BiomeControlFactor get(String key) {
            for (WetnessLevel level : values())
                if (level.key.equalsIgnoreCase(key))
                    return level;
            return null;
        }
    }

    /**
     * Enum that contains all possible humidity levels
     * for this generator.
     */
    public enum HumidityLevel implements BiomeControlFactor {
        ARID("ARID", 0.0f),
        HUMID("HUMID", 0.5f),
        VERY_HUMID("VERY_HUMID", 0.8f);

        /** The key for a temperature level.*/
        private String key;

        /** The corresponding noise value for this humidity level. */
        private float level;

        /**
         * Initialize a new HumidityLevel.
         *
         * @param key the key
         * @param level the level
         */
        HumidityLevel(String key, float level) {
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
        public float getLevel() {
            return level;
        }

        @Override
        public BiomeControlFactor get(String key) {
            for (HumidityLevel level : values())
                if (level.key.equalsIgnoreCase(key))
                    return level;
            return null;
        }
    }

    /**
     * Enum that contains all possible vegetation levels
     * for this generator.
     */
    public enum VegetationLevel implements BiomeControlFactor {
        BARE("BARE", 0.0f),
        SOME("SOME", 0.2f),
        MEDIUM("MEDIUM", 0.4f),
        LOTS("LOTS", 0.6f),
        OVERGROWN("VERY_HOT", 0.8f);

        /** The key for a vegetation level.*/
        private String key;

        /** The corresponding noise value for this vegetation level. */
        private float level;

        /**
         * Initialize a new VegetationLevel.
         *
         * @param key the key
         * @param level the level
         */
        VegetationLevel(String key, float level) {
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
        public float getLevel() {
            return level;
        }

        @Override
        public BiomeControlFactor get(String key) {
            for (VegetationLevel level : values())
                if (level.key.equalsIgnoreCase(key))
                    return level;
            return null;
        }
    }

}
