package com.github.itheos.sierra.utils;

import com.github.itheos.sierra.engine.biome.BiomeControlFactor;

/**
 * Created by PolyRocketMatt on 19/03/2021.
 */

public class ArrayUtils {

    public static BiomeControlFactor[] mergeFactors(BiomeControlFactor[] climate, BiomeControlFactor[] factors) {
        BiomeControlFactor[] array = new BiomeControlFactor[climate.length + factors.length];

        int i = 0;
        for (BiomeControlFactor climateBCF : climate)
            array[i++] = climateBCF;
        for (BiomeControlFactor factorBCF : factors)
            array[i++] = factorBCF;

        return array;
    }

}
