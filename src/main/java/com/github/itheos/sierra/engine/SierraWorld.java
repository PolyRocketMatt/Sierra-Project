package com.github.itheos.sierra.engine;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.SierraConfiguration;
import com.github.itheos.sierra.engine.biome.types.WheatFieldBiome;
import com.github.itheos.sierra.engine.generator.general.BaseGenerator;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 */

public class SierraWorld {

    private String name;
    private SierraConfiguration config;
    private BaseGenerator baseGenerator;

    //  List of biomes
    private WheatFieldBiome wheatFieldBiome;

    public SierraWorld(String name) {
        this.name = name;
        this.config = Sierra.getWorlds();
        this.baseGenerator = new BaseGenerator(config.<Integer>get("worlds." + name + ".seeds.base"));

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

    public WheatFieldBiome getWheatFieldBiome() {
        return wheatFieldBiome;
    }
}
