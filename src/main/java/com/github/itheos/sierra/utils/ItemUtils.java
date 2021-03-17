package com.github.itheos.sierra.utils;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Utilities regarding items.
 */

public class ItemUtils {

    /**
     * Compare items up to their display name. If the items
     * have the same type, amount and name, they are
     * considered equal.
     *
     * @param item the item
     * @param comparator the comparator item
     * @return true iff the items have the same type, amount and name
     */
    public static boolean isItem(@NotNull ItemStack item, @NotNull ItemStack comparator) {
        if (item.getType() == comparator.getType())
            if (item.getAmount() == comparator.getAmount())
                if (item.hasItemMeta() && comparator.hasItemMeta()) {
                    ItemMeta itemMeta = item.getItemMeta();
                    ItemMeta comparatorMeta = comparator.getItemMeta();

                    return itemMeta.hasDisplayName() && comparatorMeta.hasDisplayName() &&
                            itemMeta.getDisplayName().equalsIgnoreCase(comparatorMeta.getDisplayName());
                }

        return false;
    }

    /**
     * Get the ItemStack as a GUI item.
     *
     * @param item the ItemStack
     * @return a the GUI Item
     */
    public static GuiItem asGuiItem(ItemStack item) {
        return new GuiItem(item);
    }

    /**
     * Get the ItemStack as a GUI item and
     * pass a consumer to handle clicking the item.
     *
     * @param item the ItemStack
     * @param consumer the consumer
     * @return the GUI item
     */
    public static GuiItem asGuiItem(ItemStack item, Consumer<InventoryClickEvent> consumer) {
        return new GuiItem(item, consumer);
    }

}
