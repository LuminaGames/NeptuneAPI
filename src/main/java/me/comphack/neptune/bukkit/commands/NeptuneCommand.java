package me.comphack.neptune.bukkit.commands;

public interface NeptuneCommand {

    boolean isPlayerOnly();

    String getPermission();

    String getSyntax();

    void execute();

}
