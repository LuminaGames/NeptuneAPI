package io.github.vedantmulay.neptuneapi.bukkit.menu;

// MenuManager.java
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

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
