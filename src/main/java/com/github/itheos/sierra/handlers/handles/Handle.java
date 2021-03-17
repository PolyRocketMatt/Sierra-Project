package com.github.itheos.sierra.handlers.handles;

import com.github.itheos.sierra.Sierra;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * A handle provides parameters to be used
 * for a handler. A handler cannot use any other
 * parameters than those given to the handle.
 */

public abstract class Handle {

    /**
     * Log handle.
     */
    public Handle() { Sierra.getDefaultLogger().log("Fetching handle > " + getName()); }

    /**
     * Get the name of the handle.
     *
     * @return the name
     */
    public abstract String getName();

}
