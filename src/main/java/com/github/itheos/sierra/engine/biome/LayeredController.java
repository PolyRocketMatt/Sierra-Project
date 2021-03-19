package com.github.itheos.sierra.engine.biome;

import com.github.itheos.sierra.engine.Controller;
import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.generator.general.SimplexGenerator;

/**
 * Created by PolyRocketMatt on 18/03/2021.
 *
 * Controller that handles certain non-climate related
 * factors such as topography, wetness, humidity and
 * vegetation.
 */

public class LayeredController implements Controller {

    /**
     * Enum that contains all possible topography levels
     * for this generator.
     */
    public enum TopographyLevel {
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
    }

    /**
     * Enum that contains all possible wetness levels
     * for this generator.
     */
    public enum WetnessLevel {
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
    }

    /**
     * Enum that contains all possible humidity levels
     * for this generator.
     */
    public enum HumidityLevel {
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
    }

    /**
     * Enum that contains all possible vegetation levels
     * for this generator.
     */
    public enum VegetationLevel {
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
    }

    /** Simplex generators for topography, wetness, humidity and vegetation amounts. */
    private SimplexGenerator topographyController;
    private SimplexGenerator wetnessController;
    private SimplexGenerator humidityController;
    private SimplexGenerator vegetationController;

    /**
     * Initialize a new LayeredController.
     *
     * @param world the parent world
     */
    public LayeredController(SierraWorld world) {
        String key = "worlds." + world.getName();

        this.topographyController = new SimplexGenerator(world.getConfig().getAsInteger(key + ".seeds.controllers.topography"));
        this.wetnessController = new SimplexGenerator(world.getConfig().getAsInteger(key + ".seeds.controllers.wetness"));
        this.humidityController = new SimplexGenerator(world.getConfig().getAsInteger(key + ".seeds.controllers.humidity"));
        this.vegetationController = new SimplexGenerator(world.getConfig().getAsInteger(key + ".seeds.controllers.vegetation"));
    }

    /**
     * Get the topography controller.
     *
      * @return the topography controller
     */
    public SimplexGenerator getTopographyController() {
        return topographyController;
    }

    /**
     * Get the wetness controller.
     *
     * @return the wetness controller
     */
    public SimplexGenerator getWetnessController() {
        return wetnessController;
    }

    /**
     * Get the humidity controller.
     *
     * @return the humidity controller
     */
    public SimplexGenerator getHumidityController() {
        return humidityController;
    }

    /**
     * Get the vegetation controller.
     *
     * @return the vegetation controller
     */
    public SimplexGenerator getVegetationController() {
        return vegetationController;
    }

    @Override
    public float[][][] compute(int chunkX, int chunkZ) {
        float[][][] map = new float[16][16][4];

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                float topography = topographyController.noise(chunkX + x, chunkZ + z);
                float wetness = wetnessController.noise(chunkX + x, chunkZ + z);
                float humidity = humidityController.noise(chunkX + x, chunkZ + z);
                float vegetation = vegetationController.noise(chunkX + x, chunkZ + z);

                map[x][z] = new float[] { topography, wetness, humidity, vegetation };
            }
        }

        return map;
    }
}
