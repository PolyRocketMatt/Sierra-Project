package com.github.itheos.sierra;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 *
 * Configuration tool to handle YML files more
 * easily and uniform.
 */

public class SierraConfiguration {

    /** The file that contains the YML code */
    private File file;

    /** The file loaded as a FileConfiguration */
    private FileConfiguration configuration;

    /**
     * Initialize a new SierraConfiguration.
     *
     * @param file the file
     * @param configuration the configuration
     */
    public SierraConfiguration(File file, FileConfiguration configuration) {
        this.file = file;
        this.configuration = configuration;
    }

    /**
     * Get an attribute from the specified path.
     *
     * @param path the path
     * @param <T> the type of the attribute
     * @return the attribute
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String path) {
        return (T) configuration.get(path);
    }

    /**
     * Get a double from the config as a float
     *
     * @param path the path
     * @return the attribute
     */
    public float getAsFloat(String path) {
        return (float) configuration.getDouble(path);
    }

    public int getAsInteger(String path) {
        return configuration.getInt(path);
    }

    /**
     * Get a list of attributes from the specified path.
     *
     * @param path the path
     * @param <T> the type of the attributes
     * @return a list of attributes
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getList(String path) { return (List<T>) configuration.getList(path); }

    /*
    public Set<String> getKeys() {
        return configuration.getKeys(false);
    }
    */

    /**
     * Set a value to the specified path.
     *
     * @param path the path
     * @param value the value
     */
    public void set(String path, Object value) {
        configuration.set(path, value);
        save();
    }

    /**
     * Check to see if the configuration contains the specified path.
     *
     * @param path the path
     * @return true if the configuration contains the path
     */
    public boolean contains(String path) {
        return configuration.contains(path);
    }

    /**
     * Save the configuration.
     */
    public void save() {
        try { configuration.save(file); }
        catch (IOException ex) { Sierra.getDefaultLogger().log("Could not save configuration " + file.getName());}
    }

}