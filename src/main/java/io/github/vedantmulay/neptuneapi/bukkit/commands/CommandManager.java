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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.github.vedantmulay.neptuneapi.bukkit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements CommandExecutor {

    private final Map<String, NeptuneCommand> commands;
    private final JavaPlugin plugin;
    public static String noPermissionMessage;
    public static String playerOnlyCommandMessage;

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.commands = new HashMap<>();
    }

    public static void setNoPermissionMessage(String noPermissionMessage) {
        CommandManager.noPermissionMessage = noPermissionMessage;
    }

    public static void setPlayerOnlyCommandMessage(String playerOnlyCommandMessage) {
        CommandManager.playerOnlyCommandMessage = playerOnlyCommandMessage;
    }

    public void registerCommand(String commandName, NeptuneCommand executor) {
        commands.put(commandName, executor);
        plugin.getCommand(commandName).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName().toLowerCase();

        if (commands.containsKey(commandName)) {
            NeptuneCommand executor = commands.get(commandName);

            // Check player-only
            if (executor.isPlayerOnly() && !(sender instanceof Player)) {
                sender.sendMessage(playerOnlyCommandMessage);
                return true;
            }

            // Check permission
            if (executor.getPermission() != null && !sender.hasPermission(executor.getPermission())) {
                sender.sendMessage(noPermissionMessage);
                return true;
            }

            // Execute the command
            return executor.onCommand(sender, command, label, args);
        }

        return false; // Command not found
    }


}
