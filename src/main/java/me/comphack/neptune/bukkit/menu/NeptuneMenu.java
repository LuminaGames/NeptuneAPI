package me.comphack.neptune.bukkit.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class NeptuneMenu {

    private final String title;
    private final List<Inventory> pages;
    private int currentPageIndex;

    public NeptuneMenu(String title, List<Inventory> pages) {
        this.title = title;
        this.pages = pages;
        this.currentPageIndex = 0;
    }

    public void nextPage() {
        if (currentPageIndex < pages.size() - 1) {
            currentPageIndex++;
        }
    }

    public void previousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
        }
    }

    public Inventory getCurrentPage() {
        return pages.get(currentPageIndex);
    }

    public String getTitle() {
        return title;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public int getTotalPages() {
        return pages.size();
    }

    public void open(Player player) {
        player.openInventory(getCurrentPage());
    }
}
