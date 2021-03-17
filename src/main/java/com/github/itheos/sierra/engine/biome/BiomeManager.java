package com.github.itheos.sierra.engine.biome;

import org.bukkit.block.Biome;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 */

public class BiomeManager {

    /**
     * A list of all biomes in Sierra and their respective
     * ground-coloring in Spigot.
     */
    public enum Biomes {
        FOREST("forest", Biome.PLAINS),
        BIRCH_FOREST("birch_forest", Biome.PLAINS),
        PLAINS("plains", Biome.PLAINS),
        TAIGA("taiga", Biome.PLAINS),
        WHEAT_FIELDS("wheat_field", Biome.PLAINS);

        private String name;
        private Biome biome;

        Biomes(String name, Biome biome) {
            this.name = name;
            this.biome = biome;
        }

        public String getName() {
            return name;
        }

        public Biome getBiome() {
            return biome;
        }

        public static Biomes getBiome(String value) {
            for (Biomes biome : values())
                if (biome.name.equalsIgnoreCase(value))
                    return biome;
            return null;
        }

    }

}
