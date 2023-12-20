package me.comphack.neptune.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface MenuItem {

    ItemStack getDisplayItem();

    void onClick(Player player);
}
