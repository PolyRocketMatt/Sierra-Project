package com.github.itheos.sierra.engine.climate;

import com.github.itheos.sierra.engine.Controller;
import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.generator.climate.TemperatureGenerator;
import com.github.itheos.sierra.engine.generator.climate.WindGenerator;

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

    /**
     * Initialize a new ClimateController.
     *
     * @param parent the parent world
     */
    public ClimateController(SierraWorld parent) {
        this.parent = parent;
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

    @Override
    public float[][][] compute(int chunkX, int chunkZ) {
        float[][][] map = new float[16][16][4];

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                float temp = temperatureGenerator.noise(chunkX + x, chunkZ + z);
                float wind = temperatureGenerator.noise(chunkX + x, chunkZ + z);

                map[x][z] = new float[] { temp, wind };
            }
        }

        return map;
    }

}
