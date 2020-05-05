package xyz.acrylicstyle.simplecommands.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import util.CollectionList;

public final class Utils {
    private Utils() {}

    public static CollectionList<Player> getOnlinePlayers() {
        CollectionList<Player> players = new CollectionList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        return players;
    }
}
