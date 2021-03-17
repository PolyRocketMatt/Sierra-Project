package com.github.itheos.sierra.handlers;

import com.github.itheos.sierra.SierraPlayer;
import com.github.itheos.sierra.handlers.handles.Handle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 *
 * Handler that processes players.
 */

public class PlayerHandler extends Handler {

    private HandlerState state;
    private HashMap<UUID, SierraPlayer> playerMap;

    public PlayerHandler() {
        super();

        this.state = HandlerState.RUNNING;
        this.playerMap = new HashMap<>();

        for (Player player : Bukkit.getOnlinePlayers())
            add(player);
    }

    public SierraPlayer get(Player player) {
        return playerMap.getOrDefault(player.getUniqueId(), null);
    }

    public void add(Player player) {
        if (!playerMap.containsKey(player.getUniqueId()))
            playerMap.put(player.getUniqueId(), new SierraPlayer(player));
    }

    public void remove(Player player) {
        playerMap.remove(player.getUniqueId());
    }

    @Override
    public @Nullable Handle getHandle() {
        return null;
    }

    @Override
    public @NotNull HandlerState getState() {
        return state;
    }

    @Override
    public @NotNull String getName() {
        return "PlayerHandler";
    }

    @Override
    public void suspend() {
        playerMap.clear();
        state = HandlerState.SUSPENDED;
    }
}
