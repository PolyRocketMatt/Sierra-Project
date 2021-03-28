package com.github.itheos.sierra.engine.chunk;

import com.github.itheos.sierra.engine.SierraWorld;

/**
 * Created by PolyRocketMatt on 27/03/2021.
 *
 * A locality chunk represents a configurable new "chunk" area
 * defined by Sierra. In this locality, the heightmap is stored for
 * a certain chunk of predefined size. This grid is initialized
 * whenever a new locality is needed and speeds up base generation
 * by a lot since computing will now be done once and multiple
 * computation requests are transformed to array lookups.
 */

public class LocalityChunk {

    private SierraWorld world;
    private int size;
    private int x, z, chunkX, chunkZ;

    private float[][][] baseMap;

    public LocalityChunk(SierraWorld world, int x, int z, int size) {
        this.world = world;

        this.size = size;
        this.x = x;
        this.z = z;
        this.chunkX = x * size;
        this.chunkZ = z * size;

        float[][] map = new float[size][size];
        this.baseMap = world.getBaseGenerator().noise(map, chunkX, chunkZ, size, size);
    }

    public SierraWorld getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public boolean contains(int x, int z) {
        return x >= chunkX && z >= chunkZ && x < chunkX + size && z  < chunkZ + size;
    }

    /**
     * Sample from the initial noise map.
     *
     * @param cX the locality chunk x coordinate
     * @param cZ the locality chunk z coordinate
     * @return the base map for the requested chunk
     */
    public float[][][] sample(int cX, int cZ) {
        int sampleX = cX * 16, sampleZ = cZ * 16;

        float[][][] map = new float[16][16][3];
        if (sampleX >= 0 && sampleZ >= 0 && sampleX <= size && sampleZ <= size) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    int sX = sampleX + x;
                    int sZ = sampleZ + z;

                    map[x][z] = baseMap[sX][sZ];
                }
            }
        }

        return map;
    }
    
}
