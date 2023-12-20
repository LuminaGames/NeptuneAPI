package me.comphack.neptune.bukkit.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SimpleMenuItem implements MenuItem {

    private final ItemStack displayItem;
    private final Runnable onClickAction;

    public SimpleMenuItem(Material material, String displayName, Runnable onClickAction) {
        this.displayItem = new ItemStack(material);
        this.displayItem.getItemMeta().setDisplayName(displayName);
        this.onClickAction = onClickAction;
    }

    @Override
    public ItemStack getDisplayItem() {
        return displayItem;
    }

    @Override
    public void onClick(Player player) {
        onClickAction.run();
    }
}
