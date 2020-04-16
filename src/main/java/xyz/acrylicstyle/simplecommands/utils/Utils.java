package xyz.acrylicstyle.simplecommands.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import util.CollectionList;

import java.util.function.Consumer;

public final class Utils {
    private Utils() {}

    public static void runPlayer(Player sender, String selector, Consumer<Player> consumer) {
        switch (selector) {
            case "@a":
                getOnlinePlayers().forEach(consumer);
                break;
            case "@p":
            case "@s":
                consumer.accept(sender);
                break;
            case "@r":
                consumer.accept(getOnlinePlayers().shuffle().first());
                break;
            default:
                Player player = Bukkit.getPlayerExact(selector);
                consumer.accept(player);
                break;
        }
    }

    public static CollectionList<Player> getOnlinePlayers() {
        CollectionList<Player> players = new CollectionList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        return players;
    }
}
