package com.github.itheos.sierra.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 13/03/2021.
 *
 * Exception thrown when generating noise.
 */

public class NoiseException extends SierraException {

    public NoiseException(String cause) {
        super(cause);
    }

    @Override
    public @NotNull String getExceptionType() {
        return "NoiseException";
    }
}
