package com.github.itheos.sierra.engine.generator.general;

import com.github.itheos.sierra.engine.generator.NoiseGenerator;

/**
 * Created by PolyRocketMatt on 18/03/2021.
 *
 * Abstract representation for a noise generator to compute
 * single x- and z- tuple noise values.
 */

public abstract class ComputeNoise implements NoiseGenerator {

    public abstract float noise(float x, float z);

}
