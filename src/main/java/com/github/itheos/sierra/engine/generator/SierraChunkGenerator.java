package com.github.itheos.sierra.engine.generator;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.engine.SierraEngine;
import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.handlers.EngineHandler;
import com.github.itheos.sierra.handlers.GeneratorHandler;
import com.github.itheos.sierra.utils.WorldUtils;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 */

public class SierraChunkGenerator extends org.bukkit.generator.ChunkGenerator {

    public enum ChunkGenState {
        RUNNING,
        SUSPENDED
    }

    private SierraWorld sierraWorld;
    private SierraEngine engine;
    private ChunkGenState state;

    public SierraChunkGenerator(String name, String key) {
        WorldUtils.creationRoutine(this, key, name, false, true);

        this.sierraWorld = new SierraWorld(name);
        this.state = ChunkGenState.RUNNING;

        Sierra.getDefaultLogger().log("Created new generator for " + sierraWorld.getName());
        Sierra.getHandlerManager().getGeneratorHandler().addGenerator(this);
        Sierra.getHandlerManager().getEngineHandler().add(name, new SierraEngine(this));

        this.engine = Sierra.getHandlerManager().getEngineHandler().get(name);
    }

    public SierraWorld getSierraWorld() {
        return sierraWorld;
    }

    public ChunkGenState getState() {
        return state;
    }

    public void setState(ChunkGenState state) {
        this.state = state;
    }

    @Override
    public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int cX, int cZ, @NotNull BiomeGrid grid) {
        return engine.getChunkData(world, sierraWorld, cX, cZ, grid);
    }
}
