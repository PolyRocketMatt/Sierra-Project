package com.github.itheos.sierra.assets;

import com.github.itheos.sierra.engine.biome.BiomeManager;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by PolyRocketMatt on 15/03/2021.
 */

public class PlaceableAsset {

    private String name;
    private List<BiomeManager.Biomes> biomes;
    private List<AssetData> data;

    public PlaceableAsset(SierraAsset asset) {
        this.biomes = new ArrayList<>();
        this.data = new ArrayList<>();
        this.name = asset.getName();

        String[] stringBiomes = asset.getBiomes().substring(1, asset.getBiomes().length() - 1).split(",");
        Arrays.stream(stringBiomes).forEach(biome -> {
            BiomeManager.Biomes translated = BiomeManager.Biomes.getBiome(biome.trim());

            if (translated != null) {
                biomes.add(translated);
            }
        });

        asset.blocks.forEach(sierraBlock -> {
            data.add(new AssetData(sierraBlock));
        });
    }

    public String getName() {
        return name;
    }

    public List<BiomeManager.Biomes> getBiomes() {
        return biomes;
    }

    public List<AssetData> getData() {
        return data;
    }

    public class AssetData {

        private String data;
        private Material material;
        private int offsetX, offsetY, offsetZ;

        public AssetData(SierraBlock block) {
            this.data = block.getData();
            this.material = Material.valueOf(block.getMaterial());
            this.offsetX = (int) block.getX();
            this.offsetY = (int) block.getY();
            this.offsetZ = (int) block.getZ();
        }

        public String getData() {
            return data;
        }

        public Material getMaterial() {
            return material;
        }

        public int getOffsetX() {
            return offsetX;
        }

        public int getOffsetY() {
            return offsetY;
        }

        public int getOffsetZ() {
            return offsetZ;
        }
    }

}
