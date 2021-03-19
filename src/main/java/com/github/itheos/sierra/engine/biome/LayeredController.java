package com.github.itheos.sierra.engine.biome;

import com.github.itheos.sierra.engine.Controller;
import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.generator.general.SimplexGenerator;
import com.github.itheos.sierra.utils.StringUtils;

/**
 * Created by PolyRocketMatt on 18/03/2021.
 *
 * Controller that handles certain non-climate related
 * factors such as topography, wetness, humidity and
 * vegetation.
 */

public class LayeredController implements Controller {

    /** Generator Variables. */
    private float max;

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
        String key = StringUtils.toWorldsKey(world.getName());

        System.out.println("Ok?");

        this.topographyController = new SimplexGenerator(world.getConfig().getAsInteger(key + ".seeds.controllers.topography"));
        this.wetnessController = new SimplexGenerator(world.getConfig().getAsInteger(key + ".seeds.controllers.wetness"));
        this.humidityController = new SimplexGenerator(world.getConfig().getAsInteger(key + ".seeds.controllers.humidity"));
        this.vegetationController = new SimplexGenerator(world.getConfig().getAsInteger(key + ".seeds.controllers.vegetation"));

        this.max = topographyController.trueMax(topographyController.getOctaves());

        System.out.println("Init Layer");
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
    public BiomeControlFactor[][][] compute(int chunkX, int chunkZ) {
        BiomeControlFactor[][][] map = new BiomeControlFactor[16][16][4];

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                ControlFactors.TopographyLevel topography =
                        (ControlFactors.TopographyLevel) ControlFactors.TopographyLevel.translate(topographyController.normalizedNoise(chunkX + x, chunkZ + z));
                ControlFactors.WetnessLevel wetness =
                        (ControlFactors.WetnessLevel) ControlFactors.WetnessLevel.translate(wetnessController.normalizedNoise(chunkX + x, chunkZ + z));
                ControlFactors.HumidityLevel humidity =
                        (ControlFactors.HumidityLevel) ControlFactors.HumidityLevel.translate(humidityController.normalizedNoise(chunkX + x, chunkZ + z));
                ControlFactors.VegetationLevel vegetation =
                        (ControlFactors.VegetationLevel) ControlFactors.VegetationLevel.translate(vegetationController.normalizedNoise(chunkX + x, chunkZ + z));

                map[x][z] = new BiomeControlFactor[] { topography, wetness, humidity, vegetation };
            }
        }

        return map;
    }
}
