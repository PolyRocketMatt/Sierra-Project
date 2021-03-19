package com.github.itheos.sierra.math.function;

/**
 * Created by PolyRocketMatt on 12/03/2021.
 *
 * Function that operates on parameters of
 * the provided type.
 */

public interface Function<T> {

    /**
     * Execute a defined function.
     *
     * @param params the parameters for the function
     * @return the result of the function
     */
    T call(T... params);

}
