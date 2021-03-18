package com.github.itheos.sierra;

import com.github.itheos.sierra.command.DebugCommand;
import com.github.itheos.sierra.command.SierraCommand;
import com.github.itheos.sierra.engine.generator.SierraChunkGenerator;
import com.github.itheos.sierra.handlers.*;
import com.github.itheos.sierra.handlers.handles.JSONHandle;
import com.github.itheos.sierra.handlers.handles.PluginCommandHandle;
import com.github.itheos.sierra.listeners.ConnectionListener;
import com.github.itheos.sierra.listeners.InteractionListener;
import com.github.itheos.sierra.utils.IOUtils;
import com.github.itheos.sierra.utils.settings.Setting;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Main Unreal class, responsible for handling
 * central services.
 */

public class Sierra extends JavaPlugin {

    private static final String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Sierra" + ChatColor.DARK_GRAY + "] >> ";
    private static final String CONSOLE_PREFIX = "[Sierra] >> ";

    private static SierraLogger logger;
    private static HandlerManager handlerManager;

    private static SierraConfiguration CONFIG;
    private static SierraConfiguration GENERATORS;
    private static SierraConfiguration WORLDS;

    @Override
    public void onLoad() {
        performLoadCheck();

        logger.log("Sierra has been successfully pre-loaded");
    }

    @Override
    public void onEnable() {
        logger.log("Enabling Sierra");

        performStartupCheck();

        logger.log("Sierra has been successfully enabled");
    }

    @Override
    public void onDisable() {
        performShutdownCheck();
    }

    public static Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin("Sierra");
    }

    private void performLoadCheck() {
        //  Keep a boolean to check if values will have to be loaded
        boolean init = false;

        //  Check all IO
        if (!getPlugin().getDataFolder().exists()) {
            //  Set init
            init = true;

            //  If folders are non-existent, create them
            getPlugin().getDataFolder().mkdir();

            new File(IOUtils.getConfigPath()).mkdir();
            new File(IOUtils.getLogPath()).mkdir();
            new File(IOUtils.getWorldPath()).mkdir();
            new File(IOUtils.getAssetPath()).mkdir();
        }

        //  Create configurations
        CONFIG = IOUtils.getAsNative(IOUtils.getConfigPath() + "config.yml");
        GENERATORS = IOUtils.getAsNative(IOUtils.getConfigPath() + "generators.yml");
        WORLDS = IOUtils.getAsNative(IOUtils.getWorldPath() + "worlds.yml");

        //  Initialize the logger so we can start using it
        logger = new SierraLogger();

        //  Load all default settings
        if (init) {
            for (Setting setting : Setting.values())
                getGenerators().set(setting.getKeyValuePair().getKey(), setting.getKeyValuePair().getValue());

            logger.log("Initialized configurations");
        }
    }

    private void performStartupCheck() {
        //  Initialize handler manager
        handlerManager = new HandlerManager();

        //  Initialize handle
        PluginCommandHandle commandHandle = new PluginCommandHandle(new HashSet<SierraCommand>() {{
            add(new DebugCommand());
        }});
        logger.log("Loaded handles");

        //  Initialize command handlers
        PluginCommand command = getCommand("sierra");
        Objects.requireNonNull(command).setExecutor(new CommandHandler(commandHandle));
        // Objects.requireNonNull(command).setTabCompleter(new TabHandler(commandHandle));

        //  Initialize other handlers
        new GuiHandler();
        new EventHandler();
        new PlayerHandler();
        new GeneratorHandler();
        new EngineHandler();
        new AssetHandler(new JSONHandle());
        logger.log("Loaded handlers");

        //  Download Assets
        File dir = new File(IOUtils.getAssetPath());

        if (dir.listFiles() == null || Objects.requireNonNull(dir.listFiles()).length == 0) {
            logger.log("Connecting to database, downloading assets");

            getHandlerManager().getAssetHandler().downloadAssets();

            logger.log("Downloaded assets");
        } else
            getHandlerManager().getAssetHandler().load();

        //  Register Events
        Objects.requireNonNull(handlerManager.<EventHandler>getAsPredefined("EventHandler")).register(new InteractionListener());
        Objects.requireNonNull(handlerManager.<EventHandler>getAsPredefined("EventHandler")).register(new ConnectionListener());
        logger.log("Initialized listeners");
    }

    private void performShutdownCheck() {

        //  Disable the logger
        logger.close();
    }

    public static String getPrefix() {
        return PREFIX;
    }

    public static String getConsolePrefix() {
        return CONSOLE_PREFIX;
    }

    public static SierraLogger getDefaultLogger() {
        return logger;
    }

    public static HandlerManager getHandlerManager() {
        return handlerManager;
    }

    public static SierraConfiguration getConfiguration() {
        return CONFIG;
    }

    public static SierraConfiguration getGenerators() {
        return GENERATORS;
    }

    public static SierraConfiguration getWorlds() {
        return WORLDS;
    }

    @Override
    public @Nullable ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id) {
        return new SierraChunkGenerator(worldName, "worlds." + worldName);
    }
}
