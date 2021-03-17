package com.github.itheos.sierra.factories;

import com.github.itheos.sierra.Sierra;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Factory which can create new items.
 */

public class ItemFactory {

    /**
     * Create a new ItemStack based on the given
     * parameters.
     *
     * @param material the material
     * @param amount the amount
     * @param glow glow
     * @param name the name
     * @param lore the lore
     * @return a new ItemStack
     */
    public static ItemStack createItem(Material material, int amount, boolean glow, String name, String...lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        if (glow) {
            item.addUnsafeEnchantment(Enchantment.LUCK, 1);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);

        return item;
    }

    //  Static Items
    public static final ItemStack DEBUG_GUI =      createItem(Material.COMPASS, 1, false, Sierra.getPrefix() + ChatColor.GREEN + "Debug Menu");

    //  Static GUI Items
    public static final ItemStack PANE =            createItem(Material.GRAY_STAINED_GLASS_PANE, 1, false, "");
    public static final ItemStack CLOSE =           createItem(Material.BARRIER, 1, false, ChatColor.RED + "Close");


    public static final ItemStack DEBUG_WORLD =     createItem(Material.CARTOGRAPHY_TABLE, 1, false, ChatColor.GREEN + "World Info", ChatColor.WHITE + "Get information about the current", ChatColor.WHITE + "world.");
    public static final ItemStack DEBUG_LOCATOR =   createItem(Material.COMPASS, 1, false, ChatColor.GREEN + "Locator", ChatColor.WHITE + "Locator Tool");
    public static final ItemStack DEBUG_LOG =       createItem(Material.WRITABLE_BOOK, 1, false, ChatColor.GREEN + "Log", ChatColor.WHITE + "Print the last entries of the log");
    public static final ItemStack DEBUG_OPTIONS =   createItem(Material.GLOBE_BANNER_PATTERN, 1, false, ChatColor.GREEN + "Settings", ChatColor.WHITE + "Open the settings");
    public static final ItemStack DEBUG_STICK =     createItem(Material.STICK, 1, false, ChatColor.GREEN + "Debug Stick", ChatColor.RED + "Do not use if you don't", ChatColor.RED + "know what you're doing!");
}
