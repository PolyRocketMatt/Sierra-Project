package com.github.itheos.sierra.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 15/03/2021.
 */

public class MongoException extends SierraException {

    public MongoException(String cause) {
        super(cause);
    }

    @Override
    public @NotNull String getExceptionType() {
        return "MongoException";
    }
}
