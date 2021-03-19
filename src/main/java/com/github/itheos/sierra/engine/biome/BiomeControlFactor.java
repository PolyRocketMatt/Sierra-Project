package com.github.itheos.sierra.engine.biome;

/**
 * Created by PolyRocketMatt on 19/03/2021.
 */

public interface BiomeControlFactor {

    /**
     * Get the key for the control factor.
     *
     * @return the key
     */
    String getKey();

    /**
     * Get the control factor for the given key.
     *
     * @param key the key
     * @return the control factor if present or null
     */
    BiomeControlFactor get(String key);

}
