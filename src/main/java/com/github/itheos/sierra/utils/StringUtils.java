package com.github.itheos.sierra.utils;

import org.bukkit.ChatColor;

/**
 * Created by PolyRocketMatt on 15/03/2021.
 */

public class StringUtils {

    public static String toCoordinateString(int x, int y, int z) {
        return ChatColor.GRAY + "[" + ChatColor.RED + x + ChatColor.GRAY + ";" +
                ChatColor.GREEN + y + ChatColor.GRAY + ";" +
                ChatColor.BLUE + z + ChatColor.GRAY + "]";
    }

    public static String toWorldsKey(String name) {
        return "worlds." + name;
    }

}
