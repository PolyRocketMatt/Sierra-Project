package com.github.itheos.sierra.assets;

import com.github.itheos.sierra.engine.biome.BiomeController;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by PolyRocketMatt on 15/03/2021.
 *
 * Represents an asset that has been prepared
 * to be placed within a Minecraft world.
 */

public class PlaceableAsset {

    private String name;
    private List<BiomeController.BiomeType> biomes;
    private List<AssetData> data;

    /**
     * Initialize a new PlaceableAsset.
     *
     * @param asset the original asset pulled from the database
     */
    public PlaceableAsset(SierraAsset asset) {
        this.biomes = new ArrayList<>();
        this.data = new ArrayList<>();
        this.name = asset.getName();

        String[] stringBiomes = asset.getBiomes().substring(1, asset.getBiomes().length() - 1).split(",");
        Arrays.stream(stringBiomes).forEach(biome -> {
            BiomeController.BiomeType translated = BiomeController.BiomeType.getBiome(biome.trim());

            if (translated != null) {
                biomes.add(translated);
            }
        });

        asset.blocks.forEach(sierraBlock -> {
            data.add(new AssetData(sierraBlock));
        });
    }

    /**
     * Get the name of the asset.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the list of biomes this asset can spawn in.
     *
     * @return the list of biomes
     */
    public List<BiomeController.BiomeType> getBiomes() {
        return biomes;
    }

    /**
     * Get the asset data for this asset.
     *
     * @return the asset data
     */
    public List<AssetData> getData() {
        return data;
    }

    /**
     * Represents data of a block in the asset.
     */
    public class AssetData {

        private String data;
        private Material material;
        private int offsetX, offsetY, offsetZ;

        /**
         * Initialize new Asset Data.
         *
         * @param block the block to be used to create the data
         */
        public AssetData(SierraBlock block) {
            this.data = block.getData();
            this.material = Material.valueOf(block.getMaterial());
            this.offsetX = (int) block.getX();
            this.offsetY = (int) block.getY();
            this.offsetZ = (int) block.getZ();
        }

        /**
         * Get the block-data of the block.
         *
         * @return the block-data
         */
        public String getData() {
            return data;
        }

        /**
         * Get the material of the block.
         *
         * @return the material
         */
        public Material getMaterial() {
            return material;
        }

        /**
         * Get the offset in the X direction.
         *
         * @return the offset in X direction
         */
        public int getOffsetX() {
            return offsetX;
        }

        /**
         * Get the offset in the Y direction.
         *
         * @return the offset in Y direction
         */
        public int getOffsetY() {
            return offsetY;
        }

        /**
         * Get the offset in the Z direction.
         *
         * @return the offset in Z direction
         */
        public int getOffsetZ() {
            return offsetZ;
        }
    }

}
