package com.github.itheos.sierra.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Provide basic information for an {@code UnrealCommand}.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandInfo {

    /**
     * The name of the command.
     *
     * @return the name
     */
    String name();

    /**
     * The arguments the command expects.
     *
     * @return the arguments
     */
    String arguments();

    /**
     * The permission required to execute the command.
     *
     * @return the permission
     */
    String permission();

    /**
     * The description for the command.
     *
     * @return the description
     */
    String description();

}
