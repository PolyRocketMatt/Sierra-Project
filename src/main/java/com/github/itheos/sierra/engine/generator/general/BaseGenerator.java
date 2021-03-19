package com.github.itheos.sierra.engine.generator.general;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.engine.generator.NoiseGenerator;
import com.github.itheos.sierra.math.noise.FBM;
import com.github.itheos.sierra.math.noise.SimplexNoise;
import com.github.itheos.sierra.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 13/03/2021.
 *
 * Generator that generates base maps.
 */

public class BaseGenerator implements NoiseGenerator {

    /** Generator Variables. */
    private final int multiplier;
    private final int octaves;
    private final float scale;
    private final float persistence, lacunarity;
    private final float max;

    /** Generator Utils. */
    private SimplexNoise noise;

    /**
     * Initialize a new BaseGenerator.
     *
     * @param seed the seed for the generator
     */
    public BaseGenerator(int seed) {
        this.multiplier = Sierra.getGenerators().getAsInteger("generators.base.multiplier");
        this.octaves = Sierra.getGenerators().getAsInteger("generators.base.octaves");
        this.scale = Sierra.getGenerators().getAsFloat("generators.base.scale");
        this.persistence = Sierra.getGenerators().getAsFloat("generators.base.persistence");
        this.lacunarity = Sierra.getGenerators().getAsFloat("generators.base.lacunarity");
        this.max = trueMax(octaves);

        this.noise = new SimplexNoise(seed);
    }

    @Override
    public float[][][] noise(@NotNull float[][] map, float bX, float bZ, int lX, int lZ) {
        FBM fbm = new FBM(noise, FBM.NoiseFilter.REGULAR, octaves,
                MathUtils.toFunction(scale), MathUtils.toFunction(persistence), MathUtils.toFunction(lacunarity),
                MathUtils.toFunction(1.0f), MathUtils.toFunction(1.0f));

        map = fbm.preCompute(bX, bZ, lX, lZ);

        float[][][] result = new float[lX][lZ][1];
        float value, mix;
        for (int x = 0; x < lX; x++)
            for (int z = 0; z < lZ; z++) {
                value = map[x][z];

                //  Shape the regular noise
                //  TODO: Maybe pre-calculate sigmoid curve?
                value = MathUtils.normalize(-max, max, value);
                value = MathUtils.sigmoid(-0.35f, 0.0f, 13.0f, 1.0f, value);
                mix = MathUtils.sigmoid(-0.35f, 0.0f, 6.5f, 1.0f, value);
                value = MathUtils.normalize(0.0f, 1.0f, (value + mix) / 2);

                result[x][z][0] = value;
            }

        return result;
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
