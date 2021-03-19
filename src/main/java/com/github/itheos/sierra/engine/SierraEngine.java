package com.github.itheos.sierra.engine;

import com.github.itheos.sierra.engine.biome.BiomeController;
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
     * @param chunkX the x coordinate of the chunk
     * @param chunkZ the z coordinate of the chunk
     * @return a ChunkSchema generated for this chunk
     */
    public ChunkSchema getChunkSchema(int chunkX, int chunkZ) {
        //  Calculate base map
        float[][][] baseMap = world.getBaseGenerator().noise(new float[16][16], chunkX, chunkZ, 16, 16);

        //  Calculate biome mapping
        BiomeController.BiomeType[][] biomeMap = world.getBiomeController().compute(chunkX, chunkZ);

        //  Initialize the height-map
        int[][] heightMap = new int[16][16];

        //  Loop over the chunk blocks
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                //  Create the base height at this coordinate
                heightMap[x][z] = (int) (baseMap[x][z][0] * world.getBaseGenerator().getMultiplier());

                //  If a biome at the position has been found, increase the height-map according to the biome
                if (biomeMap[x][z] != null)
                    heightMap[x][z] += world.getBiomeController().getInstance(biomeMap[x][z]).computeBiome(chunkX, chunkZ, x, z, heightMap[x][z], baseMap[x][z][0]);
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

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                float baseValue = baseMap[x][z][0];
                int heightValue = heightMap[x][z];
                BiomeController.BiomeType biome = biomeMap[x][z];

                if (biome != null) {
                    data = world.getBiomeController().getInstance(biome).buildBiome(data, x, heightValue, z);
                    data = world.getBiomeController().getInstance(biome).populate(data, x, heightValue, z);
                } else
                    data.setBlock(x, heightValue, z, Material.GRASS_BLOCK);
            }
        }

        return data;

        /*
        float[][][] base = schema.getBase();
        int[][] heightMap = schema.getHeightMap();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                float value = base[x][z][0];
                int y = heightMap[x][z];

                //  TODO: Biome Logic Here
                if (value >= 0.98f) {
                    data = world.getWheatFieldBiome().buildBiome(data, x, y, z);
                    data = world.getWheatFieldBiome().populate(data, x, y, z);
                } else {
                    data.setBlock(x, 0, z, Material.BEDROCK);

                    if (y > 64) {
                        data.setBlock(x, y, z, Material.GRASS_BLOCK);
                        grid.setBiome(x, y, z, Biome.PLAINS);

                        for (int i = 1; i <= 4; i++)
                            data.setBlock(x, y - i, z, Material.DIRT);
                        for (int offsetY = 1; offsetY < y - 4; offsetY++)
                            data.setBlock(x, offsetY, z, Material.STONE);
                    } else {
                        data.setBlock(x, y, z, Material.SAND);
                        grid.setBiome(x, y, z, Biome.OCEAN);

                        for (int i = 1; i <= 4; i++)
                            data.setBlock(x, y - i, z, Material.SAND);
                        for (int offsetY = 1; offsetY < y - 4; offsetY++)
                            data.setBlock(x, offsetY, z, Material.STONE);

                        if (y < 60) {
                            for (int offsetY = 60; offsetY > y; offsetY--)
                                data.setBlock(x, offsetY, z, Material.WATER);
                        }
                    }
                }
            }
        }

        return data;
        */
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

        ChunkGenerator.ChunkData data = Bukkit.createChunkData(world);
        ChunkSchema schema = getChunkSchema(chunkX, chunkZ);

        return buildFromSchema(data, schema, grid, chunkX, chunkZ);
    }

}
