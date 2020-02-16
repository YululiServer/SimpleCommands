package xyz.acrylicstyle.simplecommands.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import util.CollectionList;
import util.ICollectionList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
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

    public static int levenshtein(String x, String y) {
        return Levenshtein.calculate(x, y);
    }

    public static CollectionList<String> getNamespacedIDs() {
        CollectionList<String> ids = new CollectionList<>();
        ICollectionList.asList(Material.values()).forEach(m -> ids.add(m.getKey().toString()));
        return ids;
    }

    public static CollectionList<String> similarItems(String item) {
        CollectionList<String> items = getNamespacedIDs();
        CollectionList<LevenshteinObject> levenshteinObjects = items.map(id -> new LevenshteinObject(levenshtein(id, item), id));
        levenshteinObjects.sort(Comparator.comparingInt(a -> a.number));
        return levenshteinObjects.filter(i -> i.number <= 2).map(o -> (String) o.obj);
    }

    public static Object getHandle(Object instance) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            method = instance.getClass().getDeclaredMethod("getHandle");
        } catch (NoSuchMethodException e) {
            method = instance.getClass().getSuperclass().getDeclaredMethod("getHandle");
        }
        method.setAccessible(true);
        return method.invoke(instance);
    }
}
