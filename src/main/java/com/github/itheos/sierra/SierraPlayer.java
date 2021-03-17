package com.github.itheos.sierra;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 */

public class SierraPlayer {

    private UUID uuid;
    private Player player;
    private ChestGui current, previous;

    public SierraPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.player = player;
        this.current = null;
        this.previous = null;
    }

    public UUID getUniqueID() {
        return uuid;
    }

    public Player getPlayer() {
        return player;
    }

    public ChestGui getCurrent() {
        return current;
    }

    public void setCurrent(ChestGui current) {
        this.current = current;
    }

    public ChestGui getPrevious() {
        return previous;
    }

    public void setPrevious(ChestGui previous) {
        this.previous = previous;
    }
}
