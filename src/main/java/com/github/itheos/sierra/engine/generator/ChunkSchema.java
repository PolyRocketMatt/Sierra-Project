package com.github.itheos.sierra.engine.generator;

/**
 * Created by PolyRocketMatt on 14/03/2021.
 */

public class ChunkSchema {

    private float[][][] base;
    private int[][] heightMap;

    public ChunkSchema(float[][][] base, int[][] heightMap) {
        this.base = base;
        this.heightMap = heightMap;
    }

    public float[][][] getBase() {
        return base;
    }

    public int[][] getHeightMap() {
        return heightMap;
    }
}
