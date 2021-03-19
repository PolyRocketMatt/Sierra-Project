package com.github.itheos.sierra.engine.biome;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.assets.PlaceableAsset;
import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.generator.biome.BiomeGenerator;
import com.github.itheos.sierra.engine.generator.climate.PrecipitationGenerator;
import com.github.itheos.sierra.engine.generator.climate.TemperatureGenerator;
import com.github.itheos.sierra.engine.generator.climate.WindGenerator;
import com.github.itheos.sierra.exception.BiomeException;
import com.github.itheos.sierra.exception.SierraException;
import com.github.itheos.sierra.handlers.AssetHandler;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;

/**
 * Created by PolyRocketMatt on 13/03/2021.
 *
 * Represents a biome that is part of Sierra.
 */

public abstract class SierraBiome {

    /** The world this biome instance is part of. */
    protected SierraWorld world;

    /** The type of biome this biome controls. */
    protected BiomeController.BiomeType biome;

    /** An instance of the asset handler to quickly fetch allowed assets for this biome. */
    protected AssetHandler handler;

    /** A list that contains all placeable assets for this biome. */
    protected ArrayList<PlaceableAsset> assets;

    /**
     * Initialize a new SierraBiome with the given world and
     * biome type.
     *
     * @param world the world
     * @param biome the biome type
     */
    public SierraBiome(SierraWorld world, BiomeController.BiomeType biome) {
        this.world = world;
        this.biome = biome;
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
    public BiomeController.BiomeType getBiome() {
        return biome;
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

    /**
     * Get the allowed keys that result from
     * biome generation.
     *
     * @return the keys that this biome accepts. Biomes can include duplicates
     * @throws SierraException if no keys have been defined
     */
    public String[] getKeys() throws SierraException {
        throw new BiomeException("Could not define biome");
    }

    /**
     * Get the temperature levels for this biome.
     *
     * @return the temperature levels
     */
    public abstract ControlFactors.TemperatureLevel[] getTemperatureLevels();

    /**
     * Get the wind levels for this biome.
     *
     * @return the wind levels
     */
    public abstract ControlFactors.WindLevel[] getWindLevels();

    /**
     * Get the precipitation levels for this biome.
     *
     * @return the precipitation levels
     */
    public abstract ControlFactors.PrecipitationLevel[] getPrecipitationLevels();

    /**
     * Get the topography levels for this biome.
     *
     * @return the topography levels
     */
    public abstract ControlFactors.TopographyLevel[] getTopographyLevels();

    /**
     * Get the wetness levels for this biome.
     *
     * @return the wetness levels
     */
    public abstract ControlFactors.WetnessLevel[] getWetnessLevels();

    /**
     * Get the humidity levels for this biome.
     *
     * @return the humidity levels
     */
    public abstract ControlFactors.HumidityLevel[] getHumidityLevels();

    /**
     * Get the vegetation levels for this biome.
     *
     * @return the vegetation levels
     */
    public abstract ControlFactors.VegetationLevel[] getVegetationLevels();
}
