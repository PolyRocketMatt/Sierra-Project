package com.github.itheos.sierra.handlers;

import com.github.itheos.sierra.engine.SierraEngine;
import com.github.itheos.sierra.handlers.handles.Handle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Created by PolyRocketMatt on 14/03/2021.
 *
 * Handler that manages engines.
 */

public class EngineHandler extends Handler {

    private HandlerState state;
    private HashMap<String, SierraEngine> engines;

    public EngineHandler() {
        this.state = HandlerState.RUNNING;
        this.engines = new HashMap<>();
    }

    public void add(String world, SierraEngine engine) {
        if (!engines.containsKey(world))
            engines.put(world, engine);
    }

    public void remove(String world) {
        engines.remove(world);
    }

    public SierraEngine get(String world) {
        return engines.getOrDefault(world, null);
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
        return "EngineHandler";
    }

    @Override
    public void suspend() {

    }
}
