package com.github.itheos.sierra.utils.settings;

import com.github.itheos.sierra.utils.KeyValuePair;

/**
 * Created by PolyRocketMatt on 13/03/2021.
 */

public enum Setting {

    //  Locality Settings
    LOCALITY_SIZE(new KeyValuePair<>("generators.locality.size", 512)),
    LOCALITY_PRE_LOAD(new KeyValuePair<>("generators.locality.pre-load", 10)),

    //  Base Generator Settings
    BASE_MULTIPLIER(new KeyValuePair<>("generators.base.multiplier", 71)),
    BASE_OCTAVES(new KeyValuePair<>("generators.base.octaves", 8)),
    BASE_SCALE(new KeyValuePair<>("generators.base.scale", 2750.0f)),
    BASE_PERSISTENCE(new KeyValuePair<>("generators.base.persistence", 0.5f)),
    BASE_LACUNARITY(new KeyValuePair<>("generators.base.lacunarity", 1.95f)),

    SIMPLEX_OCTAVES(new KeyValuePair<>("generators.simplex.octaves", 8)),
    SIMPLEX_SCALE(new KeyValuePair<>("generators.simplex.octaves", 5000.0f)),
    SIMPLEX_PERSISTENCE(new KeyValuePair<>("generators.simplex.octaves", 0.5f)),
    SIMPLEX_LACUNARITY(new KeyValuePair<>("generators.simplex.octaves", 1.95f)),

    //  Biome Generation Settings
    BIOME_WF_MULTIPLIER(new KeyValuePair<>("generators.biome.wheat-field.multiplier", 8)),
    BIOME_WF_OCTAVES(new KeyValuePair<>("generators.biome.wheat-field.octaves", 4)),
    BIOME_WF_SCALE(new KeyValuePair<>("generators.biome.wheat-field.scale", 150.0f)),
    BIOME_WF_PERSISTENCE(new KeyValuePair<>("generators.biome.wheat-field.persistence", 0.5f)),
    BIOME_WF_LACUNARITY(new KeyValuePair<>("generators.biome.wheat-field.lacunarity", 1.95f)),
    BIOME_WF_STRAIGHT_INFLUENCE(new KeyValuePair<>("generators.biome.wheat-field.straight-neighbour-influence", 0.25f)),
    BIOME_WF_DIAGONAL_INFLUENCE(new KeyValuePair<>("generators.biome.wheat-field.diagonal-neighbour-influence", 0.125f)),

    //  Climate Generation Settings
    TEMPERATURE_OCTAVES(new KeyValuePair<>("generators.climate.temperature.octaves", 8)),
    TEMPERATURE_SCALE(new KeyValuePair<>("generators.climate.temperature.scale", 20000.0f)),
    TEMPERATURE_PERSISTENCE(new KeyValuePair<>("generators.climate.temperature.persistence", 0.5f)),
    TEMPERATURE_LACUNARITY(new KeyValuePair<>("generators.climate.temperature.lacunarity", 1.95f)),
    TEMPERATURE_LACUNARITY_INFLUENCE(new KeyValuePair<>("generators.climate.temperature.lacunarity-influence", 0.35f)),

    WIND_OCTAVES(new KeyValuePair<>("generators.climate.wind.octaves", 8)),
    WIND_SCALE(new KeyValuePair<>("generators.climate.wind.scale", 20000.0f)),
    WIND_PERSISTENCE(new KeyValuePair<>("generators.climate.wind.persistence", 0.5f)),
    WIND_LACUNARITY(new KeyValuePair<>("generators.climate.wind.lacunarity", 1.95f)),
    WIND_WARP(new KeyValuePair<>("generators.climate.wind.warp", 0.35f)),

    ;

    private KeyValuePair<String, ?> keyValuePair;

    Setting(KeyValuePair<String, ?> keyValuePair) {
        this.keyValuePair = keyValuePair;
    }

    public KeyValuePair<String, ?> getKeyValuePair() {
        return keyValuePair;
    }
}
