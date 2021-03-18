package com.github.itheos.sierra.engine.generator.biome;

import com.github.itheos.sierra.engine.generator.NoiseGenerator;

/**
 * Created by PolyRocketMatt on 13/03/2021.
 */

public abstract class BiomeGenerator implements NoiseGenerator {

    public abstract float noise(float x, float z);

}
