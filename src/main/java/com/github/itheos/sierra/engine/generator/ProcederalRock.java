package com.github.itheos.sierra.engine.generator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

import java.util.Random;

public class ProcederalRock {

    private void removeAllDirections(Material[][][] rock, int size, float erosionLvl){
        //eastside
        Random rand = new Random(); //TODO add seed
        int y = rand.nextInt(size);
        int z = rand.nextInt(size);
        for (int x=size;x>0;x--){
            if (rock[x][z][y] != null){
                rock[x][z][y] = null;
                break;
            }
        }
        //westside
        y = rand.nextInt(size);
        z = rand.nextInt(size);
        for (int x=0;x<size;x++){
            if (rock[x][z][y] != null){
                rock[x][z][y] = null;
                break;
            }
        }
        //northside
        int x = rand.nextInt(size);
        y = rand.nextInt(size);
        for (z=0;z<size;z++){
            if (rock[x][z][y] != null){
                rock[x][z][y] = null;
                break;
            }
        }
        //southside
        x = rand.nextInt(size);
        y = rand.nextInt(size);
        for (z=size;z>0;z--){
            if (rock[x][z][y] != null){
                rock[x][z][y] = null;
                break;
            }
        }
        //topside
        x = rand.nextInt(size);
        z = rand.nextInt(size);
        for (y=size;y>0;y--){
            if (rock[x][z][y] != null){
                rock[x][z][y] = null;
                break;
            }
        }
    }

    private void generate(int size, float erosionLvl){
        Material[][][] rock = new Material[size][size][size];
        //Generate inner cube
        for (int x=0;x<size;x++){
            for (int z = 0; z < size; z++){
                for (int y = 0; y < size; y++){
                    rock[x][z][y] = Material.valueOf("STONE");
                }
            }
        }
        //Erosion
        for(int i = 0; i < erosionLvl * size / 5; i++){
            removeAllDirections(rock, size, erosionLvl);
        }
    }
}
