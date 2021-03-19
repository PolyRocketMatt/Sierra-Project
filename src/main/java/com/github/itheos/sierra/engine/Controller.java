package com.github.itheos.sierra.engine;

import com.github.itheos.sierra.engine.biome.BiomeControlFactor;

/**
 * Created by PolyRocketMatt on 18/03/2021.
 *
 * Interface for a type of noise controller.
 */

public interface Controller {

    /**
     * Compute noise values for every controller on the
     * given chunk coordinate.
     *
     * @param chunkX the x coordinate of the chunk
     * @param chunkZ the z coordinate of the chunk
     * @return an array of controller values for all blocks in the chunk
     */
    BiomeControlFactor[][][] compute(int chunkX, int chunkZ);

}

