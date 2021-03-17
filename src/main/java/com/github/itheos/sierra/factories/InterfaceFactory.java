package com.github.itheos.sierra.factories;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.utils.ItemUtils;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Factory which can create GUI interfaces.
 */

public class InterfaceFactory {

    public static ChestGui buildDebugGui() {
        ChestGui gui = setBasicParams(new ChestGui(3, Sierra.getPrefix() + ChatColor.GREEN + "Debug Menu"));
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        pane.fillWith(ItemFactory.PANE);
        pane.addItem(ItemUtils.asGuiItem(ItemFactory.DEBUG_WORLD, event -> { /* TODO: Implement */ }), 1, 1);
        pane.addItem(ItemUtils.asGuiItem(ItemFactory.DEBUG_LOCATOR, event -> { /* TODO: Implement */ }), 2, 1);
        pane.addItem(ItemUtils.asGuiItem(ItemFactory.DEBUG_LOG, event -> { /* TODO: Implement */ }), 3, 1);
        pane.addItem(ItemUtils.asGuiItem(ItemFactory.DEBUG_OPTIONS, event -> { /* TODO: Implement */ }), 4, 1);
        pane.addItem(ItemUtils.asGuiItem(ItemFactory.DEBUG_STICK, event -> {
            Player player = (Player) event.getWhoClicked();

            if (player.getInventory().firstEmpty() != -1) {
                player.getInventory().addItem(ItemFactory.DEBUG_STICK);
                player.closeInventory();
            } else
                player.sendMessage(Sierra.getPrefix() + ChatColor.RED + "You don't have spot in your inventory!");
        }), 5, 1);
        pane.addItem(ItemUtils.asGuiItem(ItemFactory.CLOSE, event -> { event.getWhoClicked().closeInventory(); }), 7, 1);
        gui.addPane(pane);

        return gui;
    }

    private static ChestGui setBasicParams(ChestGui gui) {
        gui.setOnOutsideClick(event -> event.getWhoClicked().closeInventory());
        gui.setOnGlobalClick(event -> event.setResult(Event.Result.DENY));
        gui.setOnBottomClick(event -> event.setResult(Event.Result.DENY));
        gui.setOnTopClick(event -> event.setResult(Event.Result.DENY));

        gui.setOnGlobalDrag(event -> event.setResult(Event.Result.DENY));
        gui.setOnBottomDrag(event -> event.setResult(Event.Result.DENY));
        gui.setOnTopDrag(event -> event.setResult(Event.Result.DENY));

        return gui;
    }

}
