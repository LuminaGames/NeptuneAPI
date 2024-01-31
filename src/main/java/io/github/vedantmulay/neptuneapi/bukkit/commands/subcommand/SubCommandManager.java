/*
 * Copyright (c) 2024 Lumina Games
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.github.vedantmulay.neptuneapi.bukkit.commands.subcommand;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SubCommandManager implements CommandExecutor {

    private final Map<String, SubCommand> commands;
    public static String noPermissionMessage;
    public static String unknownCommandMessage;
    public static String playerOnlyCommandMessage;

    public SubCommandManager() {
        this.commands = new HashMap<>();
    }

    public static void setNoPermissionMessage(String noPermissionMessage) {
        SubCommandManager.noPermissionMessage = noPermissionMessage;
    }

    public static void setUnknownCommandMessage(String unknownCommandMessage) {
        SubCommandManager.unknownCommandMessage = unknownCommandMessage;
    }

    public static void setPlayerOnlyCommandMessage(String playerOnlyCommandMessage) {
        SubCommandManager.playerOnlyCommandMessage = playerOnlyCommandMessage;
    }


    public void registerCommand(String commandName, SubCommand neptuneCommand) {
        commands.put(commandName.toLowerCase(), neptuneCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            // Handle commands without sub-commands
            sender.sendMessage("Usage: /" + label + " <command>");
            return true;
        }

        String subCommand = args[0].toLowerCase();
        if (commands.containsKey(subCommand)) {
            SubCommand neptuneCommand = commands.get(subCommand);

            // Check permissions
            if (neptuneCommand.getPermission() != null && !sender.hasPermission(neptuneCommand.getPermission())) {
                sender.sendMessage(noPermissionMessage);
                return true;
            }

            // Check if the command is player-only
            if (neptuneCommand.isPlayerOnly() && !(sender instanceof Player)) {
                sender.sendMessage(playerOnlyCommandMessage);
                return true;
            }

            // Execute the command with the appropriate context
            neptuneCommand.execute(sender, args);
            return true;
        }

        // Unknown command or sub-command
        sender.sendMessage(unknownCommandMessage);
        return false;
    }
}
