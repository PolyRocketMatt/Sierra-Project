package com.github.itheos.sierra.engine.generator;

import com.github.itheos.sierra.engine.biome.BiomeController;

/**
 * Created by PolyRocketMatt on 14/03/2021.
 */

public class ChunkSchema {

    private float[][][] baseMap;
    private int[][] heightMap;

    private BiomeController.BiomeType[][] biomeMap;

    public ChunkSchema(float[][][] base, int[][] heightMap, BiomeController.BiomeType[][] biomeMap) {
        this.baseMap = base;
        this.heightMap = heightMap;
        this.biomeMap = biomeMap;
    }

    public float[][][] getBaseMap() {
        return baseMap;
    }

    public int[][] getHeightMap() {
        return heightMap;
    }

    public BiomeController.BiomeType[][] getBiomeMap() {
        return biomeMap;
    }
}
