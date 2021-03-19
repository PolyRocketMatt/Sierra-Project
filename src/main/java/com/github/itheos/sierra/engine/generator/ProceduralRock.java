package com.github.itheos.sierra.engine.generator;

import com.github.itheos.sierra.utils.MathUtils;

import java.util.Random;

import static java.lang.Float.NaN;

/**
 * Lode's Class
 */
=======
  
public class ProceduralRock {

    private static void removeAllDirections(int[][][] rock, int size, float erosionLvl, Random rand){
        //east-side
        int y = rand.nextInt(size);
        int z = rand.nextInt(size);
        for (int x = size - 1; x > 0; x--) {
            if (rock[x][z][y] != 0){
                rock[x][z][y] = 0;
                break;
            }
        }
        //west-side
        y = rand.nextInt(size);
        z = rand.nextInt(size);
        for (int x = 0; x < size - 1; x++) {
            if (rock[x][z][y] != 0){
                rock[x][z][y] = 0;
                break;
            }
        }
        //north-side
        int x = rand.nextInt(size);
        y = rand.nextInt(size);
        for (z = 0; z < size - 1; z++) {
            if (rock[x][z][y] != 0){
                rock[x][z][y] = 0;
                break;
            }
        }
        //south-side
        x = rand.nextInt(size);
        y = rand.nextInt(size);
        for (z = size - 1; z > 0; z--) {
            if (rock[x][z][y] != 0){
                rock[x][z][y] = 0;
                break;
            }
        }
        //topside
        x = rand.nextInt(size);
        z = rand.nextInt(size);
        for (y = size - 1; y > 0; y--) {
            if (rock[x][z][y] != 0){
                rock[x][z][y] = 0;
                break;
            }
        }
    }

    public static int[][][] generate(int size, float erosionLvl){
        int[][][] rock = new int[size][size][size*2];
        Random rand = new Random(); //TODO add seed
        //Generate inner cube
        float scale = rand.nextFloat();
        for (int x = 0; x < size; x++)
            for (int z = 0; z < size; z++) {
                float height = (float) java.lang.Math.sqrt(size * size / 4 - (x - size / 2) * (x - size / 2) - (z - size / 2) * (z - size / 2));
                if (height == NaN || height == 0) height = 1.0f;
                for (int y = 0; y < height * (1 + scale); y++)
                    rock[x][z][y] = 1;
            }

        //Erosion
        for(int i = 0; i < erosionLvl * 3.14159265f * size * size / 5; i++)
            removeAllDirections(rock, size, erosionLvl, rand);

        return rock;
    }
}
