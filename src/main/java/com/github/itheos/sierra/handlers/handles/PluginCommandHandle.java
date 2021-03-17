package com.github.itheos.sierra.handlers.handles;

import com.github.itheos.sierra.command.SierraCommand;

import java.util.Set;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Handle used for handlers that process commands.
 */

public class PluginCommandHandle extends Handle {

    private Set<SierraCommand> commands;

    public PluginCommandHandle(Set<SierraCommand> commands) {
        this.commands = commands;
    }

    public Set<SierraCommand> getCommands() {
        return commands;
    }

    @Override
    public String getName() {
        return "PluginCommandHandle";
    }
}
