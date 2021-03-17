package com.github.itheos.sierra.handlers;

import com.github.itheos.sierra.annotations.CommandInfo;
import com.github.itheos.sierra.handlers.handles.PluginCommandHandle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Handler that processes command requests.
 */

public class TabHandler extends Handler implements TabCompleter {

    private PluginCommandHandle handle;
    private Handler.HandlerState state;

    private List<String> commands;

    public TabHandler(PluginCommandHandle handle) {
        super();

        this.handle = handle;
        this.state = Handler.HandlerState.RUNNING;
        this.commands = new ArrayList<>();

        handle.getCommands().forEach(sierraCommand -> commands.add(sierraCommand.getClass().getAnnotation(CommandInfo.class).name()));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(command.getName(), commands, Collections.singleton("sierra"));
        Collections.sort(completions);

        return completions;
    }

    @Override
    public PluginCommandHandle getHandle() {
        return handle;
    }

    @NotNull
    @Override
    public String getName() {
        return "TabHandler";
    }

    @NotNull
    @Override
    public Handler.HandlerState getState() {
        return state;
    }

    @Override
    public void suspend() {
        state = HandlerState.SUSPENDED;
    }

}
