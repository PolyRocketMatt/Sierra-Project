package com.github.itheos.sierra.handlers.handles;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by PolyRocketMatt on 15/03/2021.
 */

public class JSONHandle extends Handle {

    private ObjectMapper objectMapper;

    public JSONHandle() {
        this.objectMapper = new ObjectMapper();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public String getName() {
        return "JSONHandle";
    }
}
