package com.github.itheos.sierra.engine;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.engine.biome.BiomeController;
import com.github.itheos.sierra.engine.biome.SierraBiome;
import com.github.itheos.sierra.engine.chunk.LocalityChunk;
import com.github.itheos.sierra.engine.generator.ChunkSchema;
import com.github.itheos.sierra.engine.generator.SierraChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

/**
 * Created by PolyRocketMatt on 14/03/2021.
 *
 * The main engine that creates height-maps and
 * biomes.
 */

public class SierraEngine {

    /** The chunk generator the engine uses. */
    private SierraChunkGenerator generator;

    /** The parent world for the engine. */
    private SierraWorld world;

    /**
     * Initialize a new SierraEngine.
     *
     * @param generator the chunk generator to use
     */
    public SierraEngine(SierraChunkGenerator generator) {
        this.generator = generator;
        this.world = generator.getSierraWorld();
    }

    /**
     * Create a new ChunkSchema for given chunk coordinates.
     *
     * @param locality the locality the given chunk is located in
     * @param chunkX the x coordinate of the chunk
     * @param chunkZ the z coordinate of the chunk
     * @return a ChunkSchema generated for this chunk
     */
    public ChunkSchema getChunkSchema(LocalityChunk locality, int chunkX, int chunkZ) {
        //  Calculate base map
        int[] offsetCoordinates = Sierra.getHandlerManager().getChunkHandler().toLocalityCoordinates(chunkX, chunkZ);
        float[][][] baseMap = locality.sample(offsetCoordinates[0], offsetCoordinates[1]);

        //  Calculate min-max range
        float min = Float.MAX_VALUE, max = Float.MIN_VALUE;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (min > baseMap[x][z][0]) min = baseMap[x][z][0];
                if (max < baseMap[x][z][0]) max = baseMap[x][z][0];
            }
        }

        float[] range = new float[] { min, max };

        //  Calculate biome mapping
        BiomeController.BiomeType[][] biomeMap = world.getBiomeController().compute(baseMap, range, chunkX, chunkZ);

        //  Initialize the height-map
        int[][] heightMap = new int[16][16];

        //  Loop over the chunk blocks
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                //  Create the base height at this coordinate
                heightMap[x][z] = (int) (baseMap[x][z][0] * world.getBaseGenerator().getMultiplier());

                //  If a biome at the position has been found, increase the height-map according to the biome
                if (biomeMap[x][z] != null)
                    heightMap[x][z] = world.getBiomeController().getInstance(biomeMap[x][z]).computeBiome(chunkX, chunkZ, x, z, heightMap[x][z], baseMap[x][z][0]);
            }
        }

        //  Return a new ChunkSchema
        return new ChunkSchema(baseMap, heightMap, biomeMap);
    }

    /**
     * Build a chunk based of a schema.
     *
     * @param data the ChunkData provided by Spigot
     * @param schema the ChunkSchema provided by the engine
     * @param grid the BiomeGrid provided by Spigot
     * @param chunkX the x coordinate of the chunk
     * @param chunkZ the z coordinate of the chunk
     * @return the modified ChunkData that has now generated
     */
    public ChunkGenerator.ChunkData buildFromSchema(ChunkGenerator.ChunkData data, ChunkSchema schema, ChunkGenerator.BiomeGrid grid, int chunkX, int chunkZ) {
        float[][][] baseMap = schema.getBaseMap();
        int[][] heightMap = schema.getHeightMap();
        BiomeController.BiomeType[][] biomeMap = schema.getBiomeMap();
        SierraBiome biomeInstance;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                float baseValue = baseMap[x][z][0];
                int heightValue = heightMap[x][z];
                BiomeController.BiomeType biome = biomeMap[x][z];

                biomeInstance = world.getBiomeController().getInstance(biome);
                if (biome != null) {
                    data = biomeInstance.buildBiome(data, x, heightValue, z);
                    data = biomeInstance.populate(data, x, heightValue, z);
                } else {
                    data.setBlock(x, heightValue, z, Material.GRASS_BLOCK);
                }
            }
        }

        return data;
    }

    /**
     * Create the ChunkData for given chunk coordinates.
     *
     * @param world the Bukkit world
     * @param sierraWorld the parent Sierra world
     * @param cX the x coordinate of the chunk
     * @param cZ the z coordinate of the chunk
     * @param grid the grid provided by Spigot
     * @return final ChunkData for the given chunk
     */
    public ChunkGenerator.ChunkData getChunkData(World world, SierraWorld sierraWorld, int cX, int cZ, ChunkGenerator.BiomeGrid grid) {
        int chunkX = cX * 16, chunkZ = cZ * 16;

        //  Get the locality
        LocalityChunk locality = Sierra.getHandlerManager().getChunkHandler().getLocality(sierraWorld, cX, cZ);

        //  Generate chunk data
        ChunkGenerator.ChunkData data = Bukkit.createChunkData(world);

        //  Build Chunk schema
        ChunkSchema schema = getChunkSchema(locality, chunkX, chunkZ);

        //  Return the data build from the schema
        return buildFromSchema(data, schema, grid, chunkX, chunkZ);
    }

}
