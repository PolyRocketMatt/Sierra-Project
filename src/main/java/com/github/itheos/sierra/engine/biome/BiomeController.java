package com.github.itheos.sierra.engine.biome;

import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.climate.ClimateController;
import com.github.itheos.sierra.math.Range;
import com.github.itheos.sierra.utils.KeyValuePair;
import org.bukkit.block.Biome;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 */

public class BiomeController {

    public class BiomeTag {

        private Range temperatureRange, precipitationRange, windRange, topographyRange;

        public BiomeTag(Range temperatureRange, Range precipitationRange, Range windRange, Range topographyRange) {
            this.temperatureRange = temperatureRange;
            this.precipitationRange = precipitationRange;
            this.windRange = windRange;
            this.topographyRange = topographyRange;
        }

        public Range getTemperatureRange() {
            return temperatureRange;
        }

        public Range getPrecipitationRange() {
            return precipitationRange;
        }

        public Range getWindRange() {
            return windRange;
        }

        public Range getTopographyRange() {
            return topographyRange;
        }
    }

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

    private SierraWorld parent;

    public BiomeController(SierraWorld parent) {
        this.parent = parent;
    }

}
