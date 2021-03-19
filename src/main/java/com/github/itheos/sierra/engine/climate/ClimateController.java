package com.github.itheos.sierra.engine.climate;

import com.github.itheos.sierra.engine.Controller;
import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.biome.BiomeControlFactor;
import com.github.itheos.sierra.engine.biome.ControlFactors;
import com.github.itheos.sierra.engine.generator.climate.PrecipitationGenerator;
import com.github.itheos.sierra.engine.generator.climate.TemperatureGenerator;
import com.github.itheos.sierra.engine.generator.climate.WindGenerator;
import com.github.itheos.sierra.utils.StringUtils;

/**
 * Created by PolyRocketMatt on 18/03/2021.
 *
 * Controller that handles climate related factors
 * such as temperature, wind and precipitation.
 */

public class ClimateController implements Controller {

    /** The parent world of this controller. */
    private SierraWorld parent;

    /** Generators for temperature, wind and precipitation. */
    private TemperatureGenerator temperatureGenerator;
    private WindGenerator windGenerator;
    private PrecipitationGenerator precipitationGenerator;

    /**
     * Initialize a new ClimateController.
     *
     * @param parent the parent world
     */
    public ClimateController(SierraWorld parent) {
        this.parent = parent;

        String key = StringUtils.toWorldsKey(parent.getName());

        this.temperatureGenerator = new TemperatureGenerator(
                parent.getConfig().getAsInteger( key + ".seeds.climate.temperature"));
        this.windGenerator = new WindGenerator(
                parent.getConfig().getAsInteger(key + ".seeds.climate.wind"),
                parent.getConfig().getAsInteger(key + ".seeds.climate.wind-direction"),
                parent.getConfig().getAsInteger(key + ".seeds.climate.wind-offset"));
        this.precipitationGenerator = new PrecipitationGenerator(
                parent.getConfig().getAsInteger(key + ".seeds.climate.precipitation"));
    }

    /**
     * Get the temperature generator.
     *
     * @return the temperature generator
     */
    public TemperatureGenerator getTemperatureGenerator() {
        return temperatureGenerator;
    }

    /**
     * Get the wind generator.
     *
     * @return the wind generator.
     */
    public WindGenerator getWindGenerator() {
        return windGenerator;
    }

    /**
     * Get the precipitation generator.
     *
     * @return the precipitation generator.
     */
    public PrecipitationGenerator getPrecipitationGenerator() {
        return precipitationGenerator;
    }

    @Override
    public BiomeControlFactor[][][] compute(int chunkX, int chunkZ) {
        BiomeControlFactor[][][] map = new BiomeControlFactor[16][16][4];

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                ControlFactors.TemperatureLevel temp = (ControlFactors.TemperatureLevel) temperatureGenerator.translate(chunkX + x, chunkZ + z);
                ControlFactors.WindLevel wind = (ControlFactors.WindLevel) windGenerator.translate(chunkX + x, chunkZ + z);
                ControlFactors.PrecipitationLevel precipitation = (ControlFactors.PrecipitationLevel) precipitationGenerator.translate(chunkX + x, chunkZ + z);

                map[x][z] = new BiomeControlFactor[] { temp, wind, precipitation };
            }
        }

        return map;
    }

}
