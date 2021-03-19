package com.github.itheos.sierra.engine.generator.climate;

import com.github.itheos.sierra.engine.biome.BiomeControlFactor;
import com.github.itheos.sierra.engine.generator.general.ComputeNoise;

/**
 * Created by PolyRocketMatt on 18/03/2021.
 *
 * Abstract representation for a climate generator.
 */

public abstract class ClimateGenerator extends ComputeNoise {

    public abstract BiomeControlFactor translate(float value);

}
