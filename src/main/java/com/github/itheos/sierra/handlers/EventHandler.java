package com.github.itheos.sierra.handlers;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.handlers.handles.Handle;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Handler that processes listener requests
 */

public class EventHandler extends Handler {

    private HandlerState state;
    private List<Listener> listeners;

    public EventHandler() {
        super();

        this.state = HandlerState.RUNNING;
        this.listeners = new ArrayList<>();
    }

    /**
     * Register a listener to Bukkit.
     *
     * @param listener the listener
     */
    public void register(@NotNull Listener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);

            Bukkit.getServer().getPluginManager().registerEvents(listener, Sierra.getPlugin());
        }
    }

    @Nullable
    @Override
    public Handle getHandle() {
        return null;
    }

    @NotNull
    @Override
    public HandlerState getState() {
        return state;
    }

    @NotNull
    @Override
    public String getName() {
        return "EventHandler";
    }

    @Override
    public void suspend() {
        listeners.clear();
        state = HandlerState.SUSPENDED;
    }
}
