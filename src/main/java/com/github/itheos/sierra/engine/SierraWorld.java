package com.github.itheos.sierra.engine;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.SierraConfiguration;
import com.github.itheos.sierra.engine.biome.BiomeController;
import com.github.itheos.sierra.engine.biome.LayeredController;
import com.github.itheos.sierra.engine.climate.ClimateController;
import com.github.itheos.sierra.engine.generator.general.BaseGenerator;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 *
 * Represents a world generated by Sierra.
 */

public class SierraWorld {

    /** The name of the world. */
    private String name;

    /** The configuration file of the world. */
    private SierraConfiguration config;

    /** The base generator of the world. */
    private BaseGenerator baseGenerator;

    /** The controllers of the world. */
    private ClimateController climateController;
    private BiomeController biomeController;
    private LayeredController layeredController;

    /**
     * Initialize a new SierraWorld.
     *
     * @param name the name of the world
     */
    public SierraWorld(String name) {
        this.name = name;
        this.config = Sierra.getWorlds();
        this.baseGenerator = new BaseGenerator(config.<Integer>get("worlds." + name + ".seeds.base"));

        this.climateController = new ClimateController(this);
        this.biomeController = new BiomeController(this);
        this.layeredController = new LayeredController(this);
    }

    /**
     * Get the name of the world.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the base generator of the world.
     *
     * @return the base generator
     */
    public BaseGenerator getBaseGenerator() {
        return baseGenerator;
    }

    /**
     * Get the configuration file of the world.
     *
     * @return the configuration file
     */
    public SierraConfiguration getConfig() {
        return config;
    }

    /**
     * Get the climate controller of the world.
     *
     * @return the climate controller
     */
    public ClimateController getClimateController() {
        return climateController;
    }

    /**
     * Get the biome controller of the world.
     *
     * @return the biome controller
     */
    public BiomeController getBiomeController() {
        return biomeController;
    }

    /**
     * Get the layer controller of the world.
     *
     * @return the layer controller
     */
    public LayeredController getLayeredController() {
        return layeredController;
    }

}
