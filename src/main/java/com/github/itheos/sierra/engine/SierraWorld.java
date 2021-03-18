package com.github.itheos.sierra.engine;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.SierraConfiguration;
import com.github.itheos.sierra.engine.biome.BiomeController;
import com.github.itheos.sierra.engine.biome.LayeredController;
import com.github.itheos.sierra.engine.biome.types.WheatFieldBiome;
import com.github.itheos.sierra.engine.climate.ClimateController;
import com.github.itheos.sierra.engine.generator.general.BaseGenerator;
import com.github.itheos.sierra.engine.generator.general.SimplexGenerator;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 */

public class SierraWorld {

    private String name;
    private SierraConfiguration config;
    private BaseGenerator baseGenerator;

    private ClimateController climateController;
    private BiomeController biomeController;
    private LayeredController layeredController;

    //  List of biomes
    private WheatFieldBiome wheatFieldBiome;

    public SierraWorld(String name) {
        this.name = name;
        this.config = Sierra.getWorlds();
        this.baseGenerator = new BaseGenerator(config.<Integer>get("worlds." + name + ".seeds.base"));

        this.climateController = new ClimateController(this);
        this.biomeController = new BiomeController(this);
        this.layeredController = new LayeredController(this);

        this.wheatFieldBiome = new WheatFieldBiome(this);
    }

    public String getName() {
        return name;
    }

    public BaseGenerator getBaseGenerator() {
        return baseGenerator;
    }

    public SierraConfiguration getConfig() {
        return config;
    }

    public ClimateController getClimateController() {
        return climateController;
    }

    public BiomeController getBiomeController() {
        return biomeController;
    }

    public LayeredController getLayeredController() {
        return layeredController;
    }

    public WheatFieldBiome getWheatFieldBiome() {
        return wheatFieldBiome;
    }
}
