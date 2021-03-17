package com.github.itheos.sierra.math.noise;

import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 */

public abstract class Noise {

    private int seed;

    public Noise(int seed) {
        this.seed = seed;
    }

    public int getSeed() {
        return seed;
    }

    @NotNull
    public abstract float[] evaluate(float x, float z);

    @NotNull
    public abstract float[] evaluate(float x, float y, float z);
}
