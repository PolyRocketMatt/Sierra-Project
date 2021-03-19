package com.github.itheos.sierra.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Exception thrown by a handler.
 */

public class HandlerException extends SierraException {

    public HandlerException(String cause) {
        super(cause);
    }

    @NotNull
    @Override
    public String getExceptionType() {
        return "HandlerException";
    }
}
