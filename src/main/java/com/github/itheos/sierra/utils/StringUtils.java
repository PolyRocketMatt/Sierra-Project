package com.github.itheos.sierra.utils;

import com.github.itheos.sierra.engine.biome.LayeredController;
import com.github.itheos.sierra.engine.generator.climate.PrecipitationGenerator;
import com.github.itheos.sierra.engine.generator.climate.TemperatureGenerator;
import com.github.itheos.sierra.engine.generator.climate.WindGenerator;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by PolyRocketMatt on 15/03/2021.
 */

public class StringUtils {

    public static String toCoordinateString(int x, int y, int z) {
        return ChatColor.GRAY + "[" + ChatColor.RED + x + ChatColor.GRAY + ";" +
                ChatColor.GREEN + y + ChatColor.GRAY + ";" +
                ChatColor.BLUE + z + ChatColor.GRAY + "]";
    }

    public static String toWorldsKey(String name) {
        return "worlds." + name;
    }

    public static String[] generateKeys(TemperatureGenerator.TemperatureLevel[] temperatureLevels,
                                        WindGenerator.WindLevel[] windLevels,
                                        PrecipitationGenerator.PrecipitationLevel[] precipitationLevels,
                                        LayeredController.TopographyLevel[] topographyLevels,
                                        LayeredController.WetnessLevel[] wetnessLevels,
                                        LayeredController.HumidityLevel[] humidityLevels,
                                        LayeredController.VegetationLevel[] vegetationLevels) {
        List<String> keys = new ArrayList<>();

        for (TemperatureGenerator.TemperatureLevel temperatureLevel : temperatureLevels)
            for (WindGenerator.WindLevel windLevel : windLevels)
                for (PrecipitationGenerator.PrecipitationLevel precipitationLevel : precipitationLevels)
                    for (LayeredController.TopographyLevel topographyLevel : topographyLevels)
                        for (LayeredController.WetnessLevel wetnessLevel : wetnessLevels)
                            for (LayeredController.HumidityLevel humidityLevel : humidityLevels)
                                for (LayeredController.VegetationLevel vegetationLevel : vegetationLevels)
                                    keys.add(String.join(".", temperatureLevel.getKey(), windLevel.getKey(), precipitationLevel.getKey(),
                                            topographyLevel.getKey(), wetnessLevel.getKey(), humidityLevel.getKey(), vegetationLevel.getKey()));
        return keys.toArray(new String[keys.size()]);
    }

}
