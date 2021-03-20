package com.github.itheos.sierra.engine.generator;

import com.github.itheos.sierra.utils.MathUtils;

import java.util.Random;

import static java.lang.Float.NaN;

public class ProceduralRock {

    private static void removeAllDirections(int[][][] rock, float erosionLvl, Random rand){
        //east-side
        int y = rand.nextInt(rock[0][0].length);
        int z = rand.nextInt(rock[0].length);
        for (int x = rock.length - 1; x > 0; x--) {
            if (rock[x][z][y] != 0){
                rock[x][z][y] = 0;
                break;
            }
        }
        //west-side
        y = rand.nextInt(rock[0][0].length);
        z = rand.nextInt(rock[0].length);
        for (int x = 0; x < rock.length - 1; x++) {
            if (rock[x][z][y] != 0){
                rock[x][z][y] = 0;
                break;
            }
        }
        //north-side
        int x = rand.nextInt(rock.length);
        y = rand.nextInt(rock[0][0].length);
        for (z = 0; z < rock[0].length - 1; z++) {
            if (rock[x][z][y] != 0){
                rock[x][z][y] = 0;
                break;
            }
        }
        //south-side
        x = rand.nextInt(rock.length);
        y = rand.nextInt(rock[0][0].length);
        for (z = rock[0].length - 1; z > 0; z--) {
            if (rock[x][z][y] != 0){
                rock[x][z][y] = 0;
                break;
            }
        }
        //topside
        x = rand.nextInt(rock.length);
        z = rand.nextInt(rock[0].length);
        for (y = rock[0][0].length - 1; y > 0; y--) {
            if (rock[x][z][y] != 0){
                rock[x][z][y] = 0;
                break;
            }
        }
    }
    public static int[][][] newRock(int size, float erosionLvl){
        int[][][] rock = new int[size][size][size*2];
        Random rand = new Random();
        if(size <= 4){ return generate(size, size, erosionLvl); }
        int centerX = rand.nextInt(size - 4) + 2;
        int centerZ = rand.nextInt(size - 4) + 2;
        System.out.println("Rock: " + size + ", " + centerX + ", " + centerZ);
        if(rand.nextInt(2) == 1) {
            int[][][] subRock = generate(centerX, centerZ, erosionLvl);
            System.out.println("Rock1: " + subRock.length + ", " + subRock[0].length + ", " + subRock[0][0].length);
            for (int x = 0; x < subRock.length; x++) {
                for (int z = 0; z < subRock[0].length; z++) {
                    for (int y = 0; y < subRock[0][0].length; y++) {
                        rock[x][z][y] = subRock[x][z][y];
                    }
                }
            }
        }
        if(rand.nextInt(2) == 1) {
            int[][][] subRock = generate(centerX, size - centerZ, erosionLvl);
            System.out.println("Rock2: " + subRock.length + ", " + subRock[0].length + ", " + subRock[0][0].length);
            for (int x = 0; x < subRock.length; x++) {
                for (int z = 0; z < subRock[0].length; z++) {
                    for (int y = 0; y < subRock[0][0].length; y++) {
                        rock[x][z + centerZ][y] = subRock[x][z][y];
                    }
                }
            }
        }
        if(rand.nextInt(2) == 1) {
            int[][][] subRock = generate(size - centerX, centerZ, erosionLvl);
            System.out.println("Rock3: " + subRock.length + ", " + subRock[0].length + ", " + subRock[0][0].length);
            for (int x = 0; x < subRock.length; x++) {
                for (int z = 0; z < subRock[0].length; z++) {
                    for (int y = 0; y < subRock[0][0].length; y++) {
                        rock[x + centerX][z][y] = subRock[x][z][y];
                    }
                }
            }
        }
        if(rand.nextInt(2) == 1) {
            int[][][] subRock = generate(size - centerX, size - centerZ, erosionLvl);
            System.out.println("Rock4: " + subRock.length + ", " + subRock[0].length + ", " + subRock[0][0].length);
            for (int x = 0; x < subRock.length; x++) {
                for (int z = 0; z < subRock[0].length; z++) {
                    for (int y = 0; y < subRock[0][0].length; y++) {
                        rock[x + centerX][z + centerZ][y] = subRock[x][z][y];
                    }
                }
            }
        }
        return rock;
    }

    public static int[][][] generate(double sizeX, double sizeZ, float erosionLvl){

        int[][][] rock = new int[(int)sizeX][(int)sizeZ][(int)MathUtils.max(sizeX, sizeZ) + 2];
        if(sizeX == 0 || sizeZ == 0) { return rock; }
        Random rand = new Random(); //TODO add seed
        //Generate inner cube
        int heightParam = (int)MathUtils.min(sizeX, sizeZ) - 1;
        for (int x = 0; x < sizeX; x++)
            for (int z = 0; z < sizeZ; z++) {
                double heightSquared = (1 - (x + 0.5 - sizeX / 2) * (x + 0.5 - sizeX / 2) / (sizeX/2 * sizeX/2)
                        - (z + 0.5 - sizeZ / 2) * (z + 0.5 - sizeZ / 2) / (sizeZ/2 * sizeZ/2)) * heightParam * heightParam;
                double height = 0;
                if (heightSquared >= 0) { height = Math.sqrt(heightSquared); }
                //System.out.println(height + ", " + sizeX + ", " + sizeZ);
                //if (height == NaN || height == 0) height = 1.0f;
                for (int y = 0; y < height; y++)
                    rock[x][z][y] = 1;
            }

        //Erosion
        for(int i = 0; i < erosionLvl * 4 * 3.1415926535 / 3 * (sizeX/2 * sizeX/2) * heightParam / 5; i++)
            removeAllDirections(rock, erosionLvl, rand);

        return rock;
    }
}
