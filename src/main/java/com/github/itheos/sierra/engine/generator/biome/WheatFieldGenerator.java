package com.github.itheos.sierra.engine.generator.biome;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.engine.generator.BiomeGenerator;
import com.github.itheos.sierra.math.noise.FBM;
import com.github.itheos.sierra.math.noise.SimplexNoise;
import com.github.itheos.sierra.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 13/03/2021.
 */

public class WheatFieldGenerator extends BiomeGenerator {

    private final int multiplier;
    private final int octaves;
    private final float scale;
    private final float persistence, lacunarity;
    private final float sInfluence, dInfluence;
    private final float max;

    private FBM fbm;
    private SimplexNoise noise;

    public WheatFieldGenerator(int seed) {
        this.multiplier = Sierra.getGenerators().<Integer>get("generators.biome.wheat-field.multiplier");
        this.octaves = Sierra.getGenerators().<Integer>get("generators.biome.wheat-field.octaves");
        this.scale = Sierra.getGenerators().getAsFloat("generators.biome.wheat-field.scale");
        this.persistence = Sierra.getGenerators().getAsFloat("generators.biome.wheat-field.persistence");
        this.lacunarity = Sierra.getGenerators().getAsFloat("generators.biome.wheat-field.lacunarity");
        this.sInfluence = Sierra.getGenerators().getAsFloat("generators.biome.wheat-field.s-influence");
        this.dInfluence = Sierra.getGenerators().getAsFloat("generators.biome.wheat-field.d-influence");
        this.max = trueMax(octaves);

        this.noise = new SimplexNoise(seed);
        this.fbm = new FBM(noise, FBM.NoiseFilter.BILLOWY, octaves,
                MathUtils.toFunction(scale), MathUtils.toFunction(persistence), MathUtils.toFunction(lacunarity),
                MathUtils.toFunction(1.0f), MathUtils.toFunction(1.0f));
    }

    public int getMultiplier() {
        return multiplier;
    }

    @Override
    public float noise(float x, float z) {
        float value = MathUtils.normalize(-max, max, fbm.compute(x, z));

        //  Perform Neighbouring Analysis
        float[] sNeighbours = getStraightNeighbours(x, z);
        float[] dNeighbours = getDiagonalNeighbours(x, z);
        float straightAvg = 0.0f, diagonalAvg = 0.0f;

        for (int i = 0; i < 4; i++) {
            straightAvg += sNeighbours[i];
            diagonalAvg += dNeighbours[i];
        }

        straightAvg /= (float) sNeighbours.length;
        diagonalAvg /= (float) dNeighbours.length;

        float weight = (sInfluence * straightAvg + dInfluence * diagonalAvg) / (sInfluence + dInfluence);

        return (value + weight) / 2;
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
            amp *= 0.5f;
        }

        return max;
    }

    private float simpleNoise(float x, float z) {
        return MathUtils.normalize(-max, max, fbm.compute(x, z));
    }

    private float[] getStraightNeighbours(float x, float z) {
        return new float[] {
                simpleNoise(x + 1, z),
                simpleNoise(x - 1, z),
                simpleNoise(x, z + 1),
                simpleNoise(x, z - 1),
        };
    }

    private float[] getDiagonalNeighbours(float x, float z) {
        return new float[] {
                simpleNoise(x + 1, z + 1),
                simpleNoise(x + 1, z - 1),
                simpleNoise(x - 1, z + 1),
                simpleNoise(x - 1, z - 1)
        };
    }
}
