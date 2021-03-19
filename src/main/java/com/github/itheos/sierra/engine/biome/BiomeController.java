package com.github.itheos.sierra.engine.biome;

import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.biome.types.WheatFieldBiome;
import com.github.itheos.sierra.utils.ArrayUtils;
import com.github.itheos.sierra.utils.StringUtils;
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
        FOREST("forest", Biome.PLAINS),
        BIRCH_FOREST("birch_forest", Biome.PLAINS),
        PLAINS("plains", Biome.PLAINS),
        TAIGA("taiga", Biome.PLAINS),
        WHEAT_FIELDS("wheat_field", Biome.PLAINS);

        /** The name of the biome in Sierra. */
        private String name;

        /** The Minecraft biome this biome can represent. */
        private Biome biome;

        /**
         * Initialize a new BiomeType.
         *
         * @param name the name
         * @param biome the Minecraft biome
         */
        BiomeType(String name, Biome biome) {
            this.name = name;
            this.biome = biome;
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

        System.out.println("Ok?");

        loadKeyMap();
        loadTypeMap();

        System.out.println("Init Biome");
    }

    /**
     * Load the key map for the biome mapping.
     */
    private void loadKeyMap() {
        WheatFieldBiome.register(new WheatFieldBiome(parent), biomeKeyMap);
    }

    /**
     * Load the type map for the biome mapping.
     */
    private void loadTypeMap() {
        //  We only map biomes that have keys associated to them
        for (SierraBiome key : biomeKeyMap.values())
            biomeTypeMap.put(key.biome, key);
    }

    public BiomeType[][] compute(int chunkX, int chunkZ) {
        BiomeType[][] types = new BiomeType[16][16];
        BiomeControlFactor[][][] climate = parent.getClimateController().compute(chunkX, chunkZ);
        BiomeControlFactor[][][] factors = parent.getLayeredController().compute(chunkX, chunkZ);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                BiomeControlFactor[] controlFactors = ArrayUtils.mergeFactors(climate[x][z], factors[x][z]);
                String key = StringUtils.generateKey(controlFactors);

                //  Find match
                //  TODO: Find a default biome
                types[x][z] = biomeKeyMap.getOrDefault(key, null).getBiome();
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
