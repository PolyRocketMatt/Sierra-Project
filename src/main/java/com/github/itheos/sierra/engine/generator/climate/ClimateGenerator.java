package com.github.itheos.sierra.engine.generator.climate;

import com.github.itheos.sierra.engine.generator.NoiseGenerator;

/**
 * Created by PolyRocketMatt on 18/03/2021.
 */

public abstract class ClimateGenerator implements NoiseGenerator {

    public abstract float noise(float x, float z);

}
