package com.github.itheos.sierra.utils;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.SierraConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Utilities regarding IO.
 */

public class IOUtils {

    private static final String CONFIG_PATH =   Sierra.getPlugin().getDataFolder() + "/configuration/";
    private static final String LOG_PATH =      Sierra.getPlugin().getDataFolder() + "/logs/";
    private static final String WORLD_PATH =    Sierra.getPlugin().getDataFolder() + "/worlds/";
    private static final String ASSET_PATH =    Sierra.getPlugin().getDataFolder() + "/assets/";

    public static String getConfigPath() {
        return CONFIG_PATH;
    }

    public static String getLogPath() {
        return LOG_PATH;
    }

    public static String getWorldPath() {
        return WORLD_PATH;
    }

    public static String getAssetPath() {
        return ASSET_PATH;
    }

    /**
     * Create or locate a file at the given path.
     *
     * @param path the path of the file
     * @return the file that has been created or located
     */
    public static File createOrLocate(@NotNull String path) {
        try {
            File file = new File(path);

            if (!file.exists())
                file.createNewFile();

            return file;
        } catch (IOException ex) {
            Sierra.getDefaultLogger().log(
                    "IO Exception occurred while creating file",
                    "File > " + path,
                    "Cause > " + ex.getCause().getMessage()
            );
        }

        return null;
    }

    /**
     * Get a file at the given path.
     *
     * @param path the path of the file
     * @return the file that was to be get
     */
    public static File get(@NotNull String path) {
        File file = new File(path);

        if (file.exists())
            return file;
        else
            return createOrLocate(path);
    }

    /**
     * Try to load a YAML configuration file for the given path.
     *
     * @param path the path of the file
     * @return the YAML configuration that was to be get or created
     */
    public static FileConfiguration getAsConfiguration(@NotNull String path) {
        return YamlConfiguration.loadConfiguration(get(path));
    }

    /**
     * Try to load a native configuration for the given path.
     *
     * @param path the path of the file
     * @return the native configuration that was to be get or created
     */
    public static @NotNull SierraConfiguration getAsNative(@NotNull String path) {
        File file = get(path);
        FileConfiguration configuration = getAsConfiguration(path);

        return new SierraConfiguration(file, configuration);
    }

}
