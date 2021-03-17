package com.github.itheos.sierra.engine;

import com.github.itheos.sierra.engine.generator.ChunkSchema;
import com.github.itheos.sierra.engine.generator.SierraChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

/**
 * Created by PolyRocketMatt on 14/03/2021.
 */

public class SierraEngine {

    private SierraChunkGenerator generator;
    private SierraWorld world;

    public SierraEngine(SierraChunkGenerator generator) {
        this.generator = generator;
        this.world = generator.getSierraWorld();
    }

    public ChunkSchema getChunkSchema(int chunkX, int chunkZ) {
        //  We calculate the total heightmap of the chunk

        //  We start with it's base
        float[][][] base = world.getBaseGenerator().noise(new float[16][16], chunkX, chunkZ, 16, 16);
        int[][] heightMap = new int[16][16];

        //  Overwrite Vanilla biomes
        //  Determine biomes, heights and store in a 24x24 array
        //  (Optional) Bake chunk
        //  Build chunk

        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++) {
                heightMap[x][z] = (int) (base[x][z][0] * world.getBaseGenerator().getMultiplier());

                //  Determine biome
                heightMap[x][z] = (base[x][z][0] >= 0.98f) ?
                        world.getWheatFieldBiome()
                                .computeBiome(chunkX, chunkZ, x, z, heightMap[x][z], base[x][z][0]) :
                        heightMap[x][z];
            }

        return new ChunkSchema(base, heightMap);
    }

    public ChunkGenerator.ChunkData buildFromSchema(ChunkGenerator.ChunkData data, ChunkSchema schema, ChunkGenerator.BiomeGrid grid, int chunkX, int chunkZ) {
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
    }

    public ChunkGenerator.ChunkData getChunkData(World world, SierraWorld sierraWorld, int cX, int cZ, ChunkGenerator.BiomeGrid grid) {
        int chunkX = cX * 16, chunkZ = cZ * 16;

        ChunkGenerator.ChunkData data = Bukkit.createChunkData(world);
        ChunkSchema schema = getChunkSchema(chunkX, chunkZ);

        return buildFromSchema(data, schema, grid, chunkX, chunkZ);
    }

}
