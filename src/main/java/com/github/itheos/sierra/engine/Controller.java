package com.github.itheos.sierra.engine;

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
    float[][][] compute(int chunkX, int chunkZ);

}

