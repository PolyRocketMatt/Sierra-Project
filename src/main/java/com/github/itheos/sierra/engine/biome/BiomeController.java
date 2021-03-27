package com.github.itheos.sierra.engine.biome;

import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.biome.types.BeachBiome;
import com.github.itheos.sierra.engine.biome.types.WheatFieldBiome;
import com.github.itheos.sierra.math.Range;
import com.github.itheos.sierra.utils.ArrayMapUtils;
import com.github.itheos.sierra.utils.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.block.Biome;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 *
 * Controller to handle mappings for biomes
 * from noise values to their respective biome.
 */

public class BiomeController {

    /**
     * A list of all biomes in Sierra and their respective
     * built-in biome in Spigot.
     */
    public enum BiomeType {
        BEACH("beach", Biome.BEACH, Range.of(0.901408f, 0.957746f)),
        FOREST("forest", Biome.PLAINS, Range.of(0.0f, 0.0f)),
        BIRCH_FOREST("birch_forest", Biome.PLAINS, Range.of(0.0f, 0.0f)),
        PLAINS("plains", Biome.PLAINS, Range.of(0.0f, 0.0f)),
        TAIGA("taiga", Biome.PLAINS, Range.of(0.0f, 0.0f)),
        WHEAT_FIELDS("wheat_field", Biome.PLAINS, Range.of(0.985915f, 1.0f));

        /** The name of the biome in Sierra. */
        private String name;

        /** The Minecraft biome this biome can represent. */
        private Biome biome;

        /** The range of base values this biome can generate in. */
        private Range range;

        /**
         * Initialize a new BiomeType.
         *
         * @param name the name
         * @param biome the Minecraft biome
         */
        BiomeType(String name, Biome biome, Range range) {
            this.name = name;
            this.biome = biome;
            this.range = range;
        }

        /**
         * Get the name of the biome in Sierra.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Get the Minecraft biome this biome represents.
         *
         * @return the Minecraft biome
         */
        public Biome getBiome() {
            return biome;
        }

        /**
         * Get the range this biome can generate in.
         *
         * @return the range
         */
        public Range getRange() { return range; }

        /**
         * Get a BiomeType from a given key.
         *
         * @param key the key
         * @return the BiomeType corresponding to this key or null
         */
        public static BiomeType getBiome(String key) {
            for (BiomeType biome : values())
                if (biome.name.equalsIgnoreCase(key))
                    return biome;
            return null;
        }
    }

    /** The parent world of this controller. */
    private SierraWorld parent;

    /** A mapping that contains all possible combination of climate factors mapped to their respective biomes. */
    private Map<String, SierraBiome> biomeKeyMap;

    /** A mapping that maps a BiomeType on an instance of a SierraBiome. */
    private Map<BiomeType, SierraBiome> biomeTypeMap;

    /**
     * Initialize a new BiomeController.
     *
     * @param parent the parent world
     */
    public BiomeController(SierraWorld parent) {
        this.parent = parent;
        this.biomeKeyMap = new HashMap<>();
        this.biomeTypeMap = new HashMap<>();

        loadKeyMap();
        loadTypeMap();
    }

    /**
     * Load the key map for the biome mapping.
     */
    private void loadKeyMap() {
        WheatFieldBiome.register(new WheatFieldBiome(parent), biomeKeyMap);
        BeachBiome.register(new BeachBiome(parent), biomeKeyMap);
    }

    /**
     * Load the type map for the biome mapping.
     */
    private void loadTypeMap() {
        //  We only map biomes that have keys associated to them
        for (SierraBiome key : biomeKeyMap.values())
            biomeTypeMap.put(key.biome, key);
    }

    public BiomeType[][] compute(float[][][] baseMap, float[] range, int chunkX, int chunkZ) {
        //  Compute biomes so that all biomes lie within a min-max range
        Map<String, SierraBiome> calculatedBiomeMap = ArrayMapUtils.trim(range, biomeKeyMap);

        //  Compute factors
        BiomeType[][] types = new BiomeType[16][16];
        BiomeControlFactor[][][] climate = parent.getClimateController().compute(chunkX, chunkZ);
        BiomeControlFactor[][][] factors = parent.getLayeredController().compute(chunkX, chunkZ);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                BiomeControlFactor[] controlFactors = ArrayMapUtils.mergeFactors(climate[x][z], factors[x][z]);
                String key = StringUtils.generateKey(controlFactors);

                //  Find match within trimmed map
                //  TODO: Find a default biome
                SierraBiome biome = calculatedBiomeMap.getOrDefault(key, null);

                types[x][z] = (biome == null) ? null : biome.getBiome();
            }
        }

        return types;
    }

    /**
     * Get an instance of the given biome-type.
     *
     * @param type the biome-type
     * @return an instance of a biome
     */
    public SierraBiome getInstance(BiomeType type) {
        return biomeTypeMap.getOrDefault(type, null);
    }

}
