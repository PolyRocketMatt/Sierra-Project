package com.github.itheos.sierra.listeners;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.handlers.PlayerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 *
 * Listener that listens for connections.
 */

public class ConnectionListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onQuit(PlayerQuitEvent event) {
        Sierra.getHandlerManager().getPlayerHandler().remove(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent event) {
        Sierra.getHandlerManager().getPlayerHandler().add(event.getPlayer());
    }

}
