package com.github.itheos.sierra.engine.biome;

import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.generator.general.SimplexGenerator;

/**
 * Created by PolyRocketMatt on 18/03/2021.
 */

public class LayeredController {

    private SimplexGenerator topographyController;
    private SimplexGenerator wetnessController;
    private SimplexGenerator humidityController;
    private SimplexGenerator vegetationController;

    public LayeredController(SierraWorld world) {
        String key = "worlds." + world.getName();

        this.topographyController = new SimplexGenerator(world.getConfig().getAsInteger(key + ".seeds.controllers.topography"));
        this.wetnessController = new SimplexGenerator(world.getConfig().getAsInteger(key + ".seeds.controllers.wetness"));
        this.humidityController = new SimplexGenerator(world.getConfig().getAsInteger(key + ".seeds.controllers.humidity"));
        this.vegetationController = new SimplexGenerator(world.getConfig().getAsInteger(key + ".seeds.controllers.vegetation"));
    }

    public SimplexGenerator getTopographyController() {
        return topographyController;
    }

    public SimplexGenerator getWetnessController() {
        return wetnessController;
    }

    public SimplexGenerator getHumidityController() {
        return humidityController;
    }

    public SimplexGenerator getVegetationController() {
        return vegetationController;
    }
}
