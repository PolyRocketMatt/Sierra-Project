package com.github.itheos.sierra;

import com.github.itheos.sierra.exception.HandlerException;
import com.github.itheos.sierra.handlers.Handler;
import com.github.itheos.sierra.handlers.handles.Handle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Manage all handlers that are created
 * while using Unreal.
 */

public class HandlerManager implements Loggable {

    /** A list of all handlers */
    private List<Handler> handlers;

    /** A list of all handles */
    private HashMap<Handler, Handle> handles;

    /**
     * Initialize a new HandlerManager.
     */
    public HandlerManager() {
        this.handlers = new ArrayList<>();
        this.handles = new HashMap<>();

        Sierra.getDefaultLogger().log("Handler Manager is loaded");
    }

    /**
     * Get a list of all the active handlers.
     *
     * @return active handlers
     */
    public List<Handler> getActiveHandlers() {
        List<Handler> activeHandlers = new ArrayList<>();

        handlers.forEach(handler -> {
            if (handler.getState() == Handler.HandlerState.RUNNING)
                activeHandlers.add(handler);
        });

        return activeHandlers;
    }

    /**
     * Get a handler of a predefined type T by its name.
     *
     * @param name the name of the handler
     * @param <T> the type of the handler
     * @return handler with the provided name or null
     */
    @SuppressWarnings("unchecked")
    public @Nullable <T extends Handler> T getAsPredefined(@NotNull String name) {
        try {
            return (T) get(name);
        } catch (HandlerException ex) { ex.printStackTrace(); }

        return null;
    }

    /**
     * Get a handler by its name.
     *
     * @param name the name of the handler
     * @return handler with provided name
     * @throws HandlerException if no handler has been found
     */
    public Handler get(@NotNull String name) throws HandlerException {
        return handlers
                .stream()
                .filter(handler -> handler.getName().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new HandlerException("Handler " + name + " was not found"));
    }

    /**
     * Add a handler to the handlers.
     *
     * @param other the handler to be added
     * @throws HandlerException if handler has already been registered
     */
    public void add(@NotNull Handler other) throws HandlerException {
        if (handlers.stream().anyMatch(handler -> handler.getName().equalsIgnoreCase(other.getName())))
            throw new HandlerException("Handler " + other.getName() + " was already registered");

        handlers.add(other);
        handles.put(other, other.getHandle());
    }

    /**
     * Remove a handler from the handlers.
     *
     * @param other the handler to be removed
     * @throws HandlerException if handler wasn't registered
     */
    public void remove(@NotNull Handler other) throws HandlerException {
        if (handlers.stream().noneMatch(handler -> handler.getName().equalsIgnoreCase(other.getName())))
            throw new HandlerException("Handler " + other.getName() + " was never registered");

        handles.remove(other);
        handlers.remove(other);
    }

    @Override
    public void performLog() {
        Sierra.getDefaultLogger().log("Active Handlers > " + getActiveHandlers().size(),
                "Total Handlers > " + handlers.size(),
                "Handles > " + handles.size()
        );
    }
}
