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

package io.github.vedantmulay.neptuneapi.bukkit.menu;

// MenuManager.java
import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class MenuManager {
    private final JavaPlugin plugin;
    private final Map<Player, Menu> openMenus;

    public MenuManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.openMenus = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(new MenuListener(this), plugin);
    }

    public void openMenu(Player player, Menu menu) {
        Inventory inventory = plugin.getServer().createInventory(null, menu.getSize(), menu.getTitle());
        menu.open(player);
        openMenus.put(player, menu);
        player.openInventory(inventory);
    }

    public void handleClick(Player player, int slot) {
        Menu menu = openMenus.get(player);
        if (menu != null) {
            menu.handleClick(player, slot);
        }
    }

    public void closeMenu(Player player) {
        openMenus.remove(player);
    }
}
