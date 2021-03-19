package com.github.itheos.sierra.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 19/03/2021.
 *
 * Exception thrown when generating biomes.
 */

public class BiomeException extends SierraException {

    public BiomeException(String message) {
        super(message);
    }

    @Override
    public @NotNull String getExceptionType() {
        return "BiomeException";
    }
}
