package com.github.itheos.sierra.handlers;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.exception.HandlerException;
import com.github.itheos.sierra.handlers.handles.Handle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * A handler is a service that can be run
 * at any time or continuous for a longer
 * period of time. This acts as a service
 * that can be suspended if necessary.
 */

public abstract class Handler {

    /**
     * State which represents the handler.
     */
    public enum HandlerState {
        RUNNING,
        SUSPENDED
    }

    /**
     * Initialize a new Handler.
     */
    public Handler() {
        try {
            Sierra.getDefaultLogger().log("Loading handler > " + getName(), "State of " + getName() + " > STARTING");
            Sierra.getHandlerManager().add(this);
        } catch (HandlerException ex) { ex.printStackTrace(); }
    }

    /**
     * Get the handle that the handler should use.
     *
     * @return the handle
     */
    public abstract @Nullable Handle getHandle();

    /**
     * Get the current state of the handler.
     *
     * @return the state
     */
    public abstract @NotNull HandlerState getState();

    /**
     * Get the name of the handler.
     *
     * @return the name
     */
    public abstract @NotNull String getName();

    /**
     * Suspend the handler.
     */
    public abstract void suspend();

}
