package com.github.itheos.sierra.utils;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.SierraConfiguration;
import com.github.itheos.sierra.engine.generator.SierraChunkGenerator;
import org.bukkit.*;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 *
 * Utilities regarding worlds.
 */

public class WorldUtils {

    /**
     * The complete world creation routine.
     *
     * @param generator the generator for the world
     * @param key the key in the configuration
     * @param name the name of the world
     * @param suspend suspend actions while generation to increase performance
     * @param bake bake the chunks of the world
     */
    public static void creationRoutine(@NotNull SierraChunkGenerator generator, @NotNull String key, @NotNull String name,
                                       boolean suspend, boolean bake) {
        Bukkit.broadcastMessage(Sierra.getPrefix() + ChatColor.GRAY + "Preparing " + ChatColor.GOLD + name + ChatColor.GRAY + "!");
        Sierra.getDefaultLogger().log("Preparing new world > " + name,
                "Name > " + name,
                "Suspend > " + suspend,
                "Bake > " + bake);

        prepare(key, bake);

        Bukkit.broadcastMessage(Sierra.getPrefix() + ChatColor.GRAY + "Loading " + ChatColor.GOLD + name + ChatColor.GRAY + "!");
        Sierra.getDefaultLogger().log("Creating new world > " + name);
    }

    /**
     * Prepare the worlds-configuration to have a new world.
     *
     * @param key the key in the configuration
     * @param bake bake the chunks into custom json files
     */
    public static void prepare(@NotNull String key, boolean bake) {
        SierraConfiguration config = Sierra.getWorlds();

        config.set(key + ".date", new SimpleDateFormat("yy/MM/dd HH:mm:ss").format(new Date()));
        config.set(key + ".seed", MathUtils.intSeed());
        config.set(key + ".env", World.Environment.NORMAL.toString());
        config.set(key + ".type", WorldType.NORMAL.toString());
        config.set(key + ".baked", bake);

        //  Generating seeds
        config.set(key + ".seeds.base", MathUtils.intSeed());

        //  Biome seeds
        config.set(key + ".seeds.biome.wheat-fields", MathUtils.intSeed());

        //  Controller seeds
        config.set(key + ".seeds.controllers.topography", MathUtils.intSeed());
        config.set(key + ".seeds.controllers.wetness", MathUtils.intSeed());
        config.set(key + ".seeds.controllers.humidity", MathUtils.intSeed());
        config.set(key + ".seeds.controllers.vegetation", MathUtils.intSeed());
    }

}
