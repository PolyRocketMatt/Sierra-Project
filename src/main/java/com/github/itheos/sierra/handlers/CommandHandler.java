package com.github.itheos.sierra.handlers;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.annotations.CommandInfo;
import com.github.itheos.sierra.command.SierraCommand;
import com.github.itheos.sierra.exception.HandlerException;
import com.github.itheos.sierra.handlers.handles.PluginCommandHandle;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Handler that processes commands.
 */

public class CommandHandler extends Handler implements CommandExecutor {

    private PluginCommandHandle handle;
    private HandlerState state;

    public CommandHandler(PluginCommandHandle handle) {
        super();

        this.handle = handle;
        this.state = HandlerState.RUNNING;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (command.getName().equalsIgnoreCase("sierra"))
                    internalCommandDispatch(player, command, label, args);

                return true;
            }

            sender.sendMessage(Sierra.getPrefix() + ChatColor.RED + "Only players can execute commands!");
        } catch (HandlerException ex) { ex.printStackTrace(); }

        return true;
    }

    /**
     * Handle commands dispatched by players.
     *
     * @param player the player
     * @param command the command
     * @param label the label
     * @param args the arguments
     * @throws HandlerException if the command was not found
     */
    private void internalCommandDispatch(Player player, Command command, String label, String[] args) throws HandlerException {
        if (args.length == 0) {
            player.sendMessage(Sierra.getPrefix() + ChatColor.YELLOW + "[s] " + ChatColor.GRAY + " = Alphanumeric");
            player.sendMessage(Sierra.getPrefix() + ChatColor.YELLOW + "[b] " + ChatColor.GRAY + " = True | False");
            player.sendMessage(Sierra.getPrefix() + ChatColor.YELLOW + "[i] " + ChatColor.GRAY + " = Number");
            player.sendMessage(Sierra.getPrefix() + "\n");

            for (SierraCommand cmd : handle.getCommands()) {
                StringBuilder builder = new StringBuilder();

                CommandInfo info = cmd.getClass().getAnnotation(CommandInfo.class);
                builder.append(Sierra.getPrefix())
                        .append(ChatColor.GRAY)
                        .append(info.name())
                        .append(" ")
                        .append(info.arguments())
                        .append(" - ")
                        .append(info.description())
                        .append(" [")
                        .append(info.permission())
                        .append("]");
                player.sendMessage(builder.toString());
            }

            return;
        }

        SierraCommand cmd = handle.getCommands()
                .stream()
                .filter(sierraCommand -> sierraCommand.getClass().getAnnotation(CommandInfo.class).name().equalsIgnoreCase(args[0]))
                .findAny()
                .orElseThrow(() -> new HandlerException(player.getName() + " tried executing a command \"" + args[0] + "\" that does not exist"));

        if (cmd != null) {
            CommandInfo info = cmd.getClass().getAnnotation(CommandInfo.class);

            if (player.hasPermission(info.permission())) {
                List<String> arguments = new ArrayList<>(Arrays.asList(args));

                arguments.remove(0);
                cmd.execute(player, arguments.toArray(new String[arguments.size()]));

                return;
            }

            player.sendMessage(Sierra.getPrefix() + ChatColor.RED + "Oops! You do not have the permission to do that");
        }

        player.sendMessage(Sierra.getPrefix() + ChatColor.RED + "That command does not exist!");
    }

    @Override
    public PluginCommandHandle getHandle() {
        return handle;
    }

    @NotNull
    @Override
    public String getName() {
        return "CommandHandler";
    }

    @NotNull
    @Override
    public HandlerState getState() {
        return state;
    }

    @Override
    public void suspend() {
        handle.getCommands().clear();
        state = HandlerState.SUSPENDED;
    }
}
