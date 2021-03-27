package com.github.itheos.sierra.utils;

import com.github.itheos.sierra.engine.biome.BiomeControlFactor;
import com.github.itheos.sierra.engine.biome.SierraBiome;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PolyRocketMatt on 19/03/2021.
 */

public class ArrayMapUtils {

    public static BiomeControlFactor[] mergeFactors(BiomeControlFactor[] climate, BiomeControlFactor[] factors) {
        BiomeControlFactor[] array = new BiomeControlFactor[climate.length + factors.length];

        int i = 0;
        for (BiomeControlFactor climateBCF : climate)
            array[i++] = climateBCF;
        for (BiomeControlFactor factorBCF : factors)
            array[i++] = factorBCF;

        return array;
    }

    public static Map<String, SierraBiome> trim(float[] range, Map<String, SierraBiome> biomeMap) {
        Map<String, SierraBiome> resultMap = new HashMap<>();

        for (String key : biomeMap.keySet()) {
            SierraBiome biome = biomeMap.get(key);

            if (biome.getBiome().getRange().isInRange(range))
                resultMap.put(key, biome);
        }

        return resultMap;
    }

}
