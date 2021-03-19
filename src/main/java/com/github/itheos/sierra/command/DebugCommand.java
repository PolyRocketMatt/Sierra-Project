package com.github.itheos.sierra.command;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.annotations.CommandInfo;
import com.github.itheos.sierra.factories.ItemFactory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Debug Command allows to open the debug menu
 * to debug Sierra while it is generating.
 */

@CommandInfo(
        name = "debug",
        arguments = "",
        permission = "sierra.admin",
        description = "Receive the debug compass."
)
public class DebugCommand extends SierraCommand {

    @Override
    public void execute(@NotNull Player player, @NotNull String[] args) {
        if (player.getInventory().firstEmpty() != -1)
            player.getInventory().addItem(ItemFactory.DEBUG_GUI);
        else
            player.sendMessage(Sierra.getPrefix() + ChatColor.RED + "You don't have spot in your inventory!");
    }

}
