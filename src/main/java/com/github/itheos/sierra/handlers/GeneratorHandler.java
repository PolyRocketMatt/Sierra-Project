package com.github.itheos.sierra.handlers;

import com.github.itheos.sierra.engine.generator.SierraChunkGenerator;
import com.github.itheos.sierra.handlers.handles.Handle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PolyRocketMatt on 13/03/2021.
 */

public class GeneratorHandler extends Handler {

    private HandlerState state;
    private List<SierraChunkGenerator> generators;

    public GeneratorHandler() {
        super();

        this.state = HandlerState.RUNNING;
        this.generators = new ArrayList<>();
    }

    public List<SierraChunkGenerator> getGenerators() {
        return generators;
    }

    public void addGenerator(@NotNull SierraChunkGenerator generator) {
        this.generators.add(generator);
    }

    @Override
    public @Nullable Handle getHandle() {
        return null;
    }

    @NotNull
    @Override
    public Handler.HandlerState getState() {
        return state;
    }

    @Override
    public @NotNull String getName() {
        return "GeneratorHandler";
    }

    @Override
    public void suspend() {
        generators.forEach(generator -> generator.setState(SierraChunkGenerator.ChunkGenState.SUSPENDED));
        state = HandlerState.SUSPENDED;
    }
}
