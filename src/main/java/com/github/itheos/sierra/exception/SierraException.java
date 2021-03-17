package com.github.itheos.sierra.exception;

import com.github.itheos.sierra.Sierra;
import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Uniform exception management.
 */

public abstract class SierraException extends Exception {

    /**
     * Initialize a new SierraException with the
     * given cause.
     *
     * @param cause the cause of the exception
     */
    public SierraException(String cause) {
        super(cause);

        Sierra.getDefaultLogger().log(getExceptionType() + " was thrown!", "Cause > " + cause);
    }

    /**
     * Get the type of the exception.
     *
     * @return the type of the exception
     */
    public abstract @NotNull String getExceptionType();

}
