package com.github.itheos.sierra.engine.biome;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.assets.PlaceableAsset;
import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.generator.biome.BiomeGenerator;
import com.github.itheos.sierra.handlers.AssetHandler;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by PolyRocketMatt on 13/03/2021.
 */

public abstract class SierraBiome {

    protected SierraWorld world;
    protected BiomeController.Biomes biome;
    protected BiomeController.BiomeTag tag;
    protected AssetHandler handler;
    protected ArrayList<PlaceableAsset> assets;

    public SierraBiome(SierraWorld world, BiomeController.Biomes biome, BiomeController.BiomeTag tag) {
        this.world = world;
        this.biome = biome;
        this.tag = tag;
        this.handler = Sierra.getHandlerManager().getAssetHandler();
        this.assets = new ArrayList<>();

        handler.getPlaceableAssets().forEach(asset -> {
            if (asset.getBiomes().contains(biome))
                assets.add(asset);
        });
    }

    /**
     * Get the actual biome representation.
     *
     * @return the biome
     */
    public BiomeController.Biomes getBiome() {
        return biome;
    }

    /**
     * Get the biome tag for this biome.
     *
     * @return the tag
     */
    public BiomeController.BiomeTag getTag() {
        return tag;
    }

    /**
     * Get the generator used for this biome.
     *
     * @return the generator
     */
    public abstract BiomeGenerator getGenerator();

    /**
     * Compute a biome for a single x and z coordinate
     *
     * @param cX the chunk x coordinate
     * @param cZ the chunk z coordinate
     * @param x the x coordinate
     * @param z the z coordinate
     * @param base the base value for the coordinate
     * @return y coordinate of the position
     */
    public abstract int computeBiome(int cX, int cZ, int x, int z, int base, float baseValue);

    /**
     * Build a biome for a single x and z coordinate
     *
     * @param data the chunk data
     * @param x the x coordinate within the chunk
     * @param y the y coordinate within the chunk
     * @param z the z coordinate within the chunk
     * @return manipulated chunk data according to the biome rules
     */
    public abstract ChunkGenerator.ChunkData buildBiome(ChunkGenerator.ChunkData data, int x, int y, int z);

    /**
     * Populate the given chunk data with biome features
     *
     * @param data the chunk data
     * @param x the x coordinate within the chunk
     * @param y the y coordinate within the chunk
     * @param z the z coordinate within the chunk
     * @return manipulated chunk data according to the biome populate rules
     */
    public abstract ChunkGenerator.ChunkData populate(ChunkGenerator.ChunkData data, int x, int y, int z);

}
