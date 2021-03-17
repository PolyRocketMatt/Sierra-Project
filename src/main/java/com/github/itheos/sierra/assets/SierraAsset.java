package com.github.itheos.sierra.assets;

import java.util.ArrayList;

/**
 * Created by PolyRocketMatt on 15/03/2021.
 */

public class SierraAsset {

    private String _id;
    private String world;
    private String biomes;
    private String author;
    private String name;
    private int length, width, height;
    public ArrayList<SierraBlock> blocks = new ArrayList<>();
    private String __v;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getBiomes() {
        return biomes;
    }

    public void setBiomes(String biomes) {
        this.biomes = biomes;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }
}
