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
