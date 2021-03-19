package com.github.itheos.sierra.engine.generator.general;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.math.noise.FBM;
import com.github.itheos.sierra.math.noise.SimplexNoise;
import com.github.itheos.sierra.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 18/03/2021.
 *
 * Simple simplex generator for varied uses.
 */

public class SimplexGenerator extends ComputeNoise {

    /** Generator Variables. */
    private int octaves;
    private float scale, persistence, lacunarity, max;

    /** Generator Utils. */
    private FBM fbm;

    /**
     * Initialize a new SimplexGenerator.
     *
     * @param seed the seed for the generator
     */
    public SimplexGenerator(int seed) {
        this.octaves = Sierra.getGenerators().getAsInteger("generators.simplex.octaves");
        this.scale = Sierra.getGenerators().getAsInteger("generators.simplex.scale");
        this.persistence = Sierra.getGenerators().getAsInteger("generators.simplex.persistence");
        this.lacunarity = Sierra.getGenerators().getAsInteger("generators.simplex.lacunarity");
        this.max = trueMax(octaves);

        this.fbm = new FBM(new SimplexNoise(seed), FBM.NoiseFilter.REGULAR, octaves,
                MathUtils.toFunction(scale), MathUtils.toFunction(persistence), MathUtils.toFunction(lacunarity),
                MathUtils.toFunction(1.0f), MathUtils.toFunction(1.0f));
    }

    @Override
    public float noise(float x, float z) {
        return fbm.compute(x, z);
    }

    @Override
    public float[][][] noise(@NotNull float[][] map, float bX, float bZ, int lX, int lZ) {
        return new float[lX][lZ][1];
    }

    @Override
    public float trueMax(int octaves) {
        float max = 0.0f;
        float amp = 1.0f;
        for (int i = 0; i < octaves; i++) {
            max += 1.0f * amp;
            amp *= persistence;
        }

        return max;
    }
}
