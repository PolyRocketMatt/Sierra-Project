package com.github.itheos.sierra.utils.settings;

import com.github.itheos.sierra.utils.KeyValuePair;

/**
 * Created by PolyRocketMatt on 13/03/2021.
 */

public enum Setting {

    //  Base Generator Settings
    BASE_MULTIPLIER(new KeyValuePair<>("generators.base.multiplier", 71)),
    BASE_OCTAVES(new KeyValuePair<>("generators.base.octaves", 8)),
    BASE_SCALE(new KeyValuePair<>("generators.base.scale", 2750.0f)),
    BASE_PERSISTENCE(new KeyValuePair<>("generators.base.persistence", 0.5f)),
    BASE_LACUNARITY(new KeyValuePair<>("generators.base.lacunarity", 1.95f)),

    BIOME_WF_MULTIPLIER(new KeyValuePair<>("generators.biome.wheat-field.multiplier", 8)),
    BIOME_WF_OCTAVES(new KeyValuePair<>("generators.biome.wheat-field.octaves", 4)),
    BIOME_WF_SCALE(new KeyValuePair<>("generators.biome.wheat-field.scale", 150.0f)),
    BIOME_WF_PERSISTENCE(new KeyValuePair<>("generators.biome.wheat-field.persistence", 0.5f)),
    BIOME_WF_LACUNARITY(new KeyValuePair<>("generators.biome.wheat-field.lacunarity", 1.95f)),
    BIOME_WF_STRAIGHT_INFLUENCE(new KeyValuePair<>("generators.biome.wheat-field.straight-neighbour-influence", 0.25f)),
    BIOME_WF_DIAGONAL_INFLUENCE(new KeyValuePair<>("generators.biome.wheat-field.diagonal-neighbour-influence", 0.125f));

    private KeyValuePair<String, ?> keyValuePair;

    Setting(KeyValuePair<String, ?> keyValuePair) {
        this.keyValuePair = keyValuePair;
    }

    public KeyValuePair<String, ?> getKeyValuePair() {
        return keyValuePair;
    }
}
