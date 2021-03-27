package com.github.itheos.sierra.engine.biome.types;

import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.biome.BiomeController;
import com.github.itheos.sierra.engine.biome.ControlFactors;
import com.github.itheos.sierra.engine.biome.SierraBiome;
import com.github.itheos.sierra.engine.generator.biome.BiomeGenerator;
import com.github.itheos.sierra.math.Range;
import com.github.itheos.sierra.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;

import java.util.Map;

/**
 * Created by PolyRocketMatt on 20/03/2021.
 */

public class BeachBiome extends SierraBiome {

    public BeachBiome(SierraWorld world) {
        super(world, BiomeController.BiomeType.BEACH);
    }

    @Override
    public BiomeGenerator getGenerator() {
        return null;
    }

    @Override
    public int computeBiome(int cX, int cZ, int x, int z, int base, float baseValue) {
        return base;
    }

    @Override
    public ChunkGenerator.ChunkData buildBiome(ChunkGenerator.ChunkData data, int x, int y, int z) {
        data.setBlock(x, 0, z, Material.BEDROCK);

        for (int offsetY = y; offsetY >= y - 8; offsetY--)
            data.setBlock(x, offsetY, z, Material.SAND);

        return data;
    }

    @Override
    public ChunkGenerator.ChunkData populate(ChunkGenerator.ChunkData data, int x, int y, int z) {
        return data;
    }

    public static String[] getKeys() {
        return StringUtils.generateKeys(getTemperatureLevels(), getWindLevels(), getPrecipitationLevels(), getTopographyLevels(),
                getWetnessLevels(), getHumidityLevels(), getVegetationLevels());
    }

    public static ControlFactors.TemperatureLevel[] getTemperatureLevels() {
        return new ControlFactors.TemperatureLevel[] { ControlFactors.TemperatureLevel.FREEZING, ControlFactors.TemperatureLevel.COLD, ControlFactors.TemperatureLevel.LUKEWARM, ControlFactors.TemperatureLevel.HOT, ControlFactors.TemperatureLevel.VERY_HOT };
    }

    public static ControlFactors.WindLevel[] getWindLevels() {
        return new ControlFactors.WindLevel[] { ControlFactors.WindLevel.EXTREMELY_WINDY, ControlFactors.WindLevel.CALM, ControlFactors.WindLevel.WINDY };
    }

    public static ControlFactors.PrecipitationLevel[] getPrecipitationLevels() {
        return new ControlFactors.PrecipitationLevel[] { ControlFactors.PrecipitationLevel.DRY, ControlFactors.PrecipitationLevel.REGULAR_WET, ControlFactors.PrecipitationLevel.WET, ControlFactors.PrecipitationLevel.VERY_WET, ControlFactors.PrecipitationLevel.VERY_DRY };
    }

    public static ControlFactors.TopographyLevel[] getTopographyLevels() {
        return new ControlFactors.TopographyLevel[] { ControlFactors.TopographyLevel.FLAT, ControlFactors.TopographyLevel.HILLY, ControlFactors.TopographyLevel.HILLS, ControlFactors.TopographyLevel.EXTREME_HILLS, ControlFactors.TopographyLevel.MOUNTAINOUS };
    }

    public static ControlFactors.WetnessLevel[] getWetnessLevels() {
        return new ControlFactors.WetnessLevel[] { ControlFactors.WetnessLevel.DRY, ControlFactors.WetnessLevel.WET, ControlFactors.WetnessLevel.EXTREMELY_WET };
    }

    public static ControlFactors.HumidityLevel[] getHumidityLevels() {
        return new ControlFactors.HumidityLevel[] { ControlFactors.HumidityLevel.ARID, ControlFactors.HumidityLevel.HUMID, ControlFactors.HumidityLevel.VERY_HUMID };
    }

    public static ControlFactors.VegetationLevel[] getVegetationLevels() {
        return new ControlFactors.VegetationLevel[] { ControlFactors.VegetationLevel.BARE, ControlFactors.VegetationLevel.SOME, ControlFactors.VegetationLevel.MEDIUM, ControlFactors.VegetationLevel.LOTS, ControlFactors.VegetationLevel.OVERGROWN };
    }

    public static <T extends SierraBiome> void register(T instance, Map<String, SierraBiome> biomeMap) {
        for (String key : getKeys())
            biomeMap.put(key, instance);
    }

}
