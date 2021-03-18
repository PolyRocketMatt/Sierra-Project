package com.github.itheos.sierra.engine.climate;

import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.generator.climate.TemperatureGenerator;
import com.github.itheos.sierra.engine.generator.climate.WindGenerator;

/**
 * Created by PolyRocketMatt on 18/03/2021.
 */

public class ClimateController {

    private SierraWorld parent;

    private TemperatureGenerator temperatureGenerator;
    private WindGenerator windGenerator;

    public ClimateController(SierraWorld parent) {
        this.parent = parent;
    }

    public SierraWorld getParent() {
        return parent;
    }
}
