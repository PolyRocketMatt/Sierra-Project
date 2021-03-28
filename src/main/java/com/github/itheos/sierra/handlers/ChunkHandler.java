package com.github.itheos.sierra.handlers;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.chunk.LocalityChunk;
import com.github.itheos.sierra.handlers.handles.Handle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by PolyRocketMatt on 27/03/2021.
 */

public class ChunkHandler extends Handler {

    private HandlerState state;
    private Set<LocalityChunk> localityChunks;

    private int size;

    public ChunkHandler() {
        this.state = HandlerState.RUNNING;
        this.localityChunks = new HashSet<>();

        this.size = Sierra.getGenerators().getAsInteger("generators.locality.size");
    }

    /**
     * Get the locality for given chunk coordinates.
     *
     * @param world the world of the locality
     * @param chunkX the x coordinate of the chunk
     * @param chunkZ the z coordinate of the chunk
     * @return the found or newly created locality
     */
    public LocalityChunk getLocality(SierraWorld world, int chunkX, int chunkZ) {
        //  If a locality exists that contains x and z, return the existing one.
        //  If no locality exists, create a new one
        return localityChunks
                .stream()
                .filter(localityChunk -> localityChunk.contains(chunkX, chunkZ) && localityChunk.getWorld().equals(world))
                .findFirst()
                .orElseGet(() -> make(world, chunkX, chunkZ));
    }

    private LocalityChunk make(SierraWorld world, int x, int z) {
        int[] baseCoordinates = toBaseCoordinates(x, z);
        LocalityChunk chunk = new LocalityChunk(world, baseCoordinates[0], baseCoordinates[1], size);

        localityChunks.add(chunk);

        return chunk;
    }

    private int[] toBaseCoordinates(int x, int z) {
        return new int[] { x % size, z % size };
    }

    public int[] toLocalityCoordinates(int x, int z) {
        int[] baseCoordinates = toBaseCoordinates(x, z);

        baseCoordinates[0] *= (int) (size / 16);
        baseCoordinates[1] *= (int) (size / 16);

        return new int[] {
                x - baseCoordinates[0],
                z - baseCoordinates[1]
        };
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
        return "ChunkHandler";
    }

    @Override
    public void suspend() {}
}
