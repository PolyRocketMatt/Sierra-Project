package com.github.itheos.sierra.engine.generator;

import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 *
 * @immutable Noise generator function.
 */

public interface NoiseGenerator {

    /**
     * Calculate noise map for a predefined map of length
     * lX and lZ.
     *
     * @param map the to which values are to be calculated
     * @param bX the base x coordinate
     * @param bZ the base z coordinate
     * @param lX the length of the map
     * @param lZ the depth of the map
     * @return a map with for every coordinate 3 values: the noise
     *          value and the two derivatives
     */
    float[][][] noise(@NotNull float[][] map, float bX, float bZ, int lX, int lZ);

    /**
     * Calculate the true maximum value that the noise
     * generator will return.
     *
     * @return true maximum value of noise
     */
    float trueMax(int octaves);

}
