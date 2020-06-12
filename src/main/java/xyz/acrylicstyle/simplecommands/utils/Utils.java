package xyz.acrylicstyle.simplecommands.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import util.CollectionList;
import util.reflect.Ref;
import xyz.acrylicstyle.tomeito_api.utils.ReflectionUtil;

public final class Utils {
    private Utils() {}

    public static CollectionList<Player> getOnlinePlayers() {
        CollectionList<Player> players = new CollectionList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        return players;
    }

    public static Object getPacket(int i, float f) {
        return Ref.forName(ReflectionUtil.getNMSPackage() + ".PacketPlayOutGameStateChange")
                .getConstructor(int.class, float.class)
                .newInstance(i, f);
    }

    public static void sendPacket(Player player, Object packet) {
        Object handle = Ref.forName(ReflectionUtil.getCraftBukkitPackage() + ".entity.CraftPlayer")
                .getMethod("getHandle")
                .invoke(player);
        Object playerConnection = Ref.forName(ReflectionUtil.getNMSPackage() + ".EntityPlayer")
                .getField("playerConnection")
                .get(handle);
        Ref.forName(ReflectionUtil.getNMSPackage() + ".PlayerConnection")
                .getMethod("sendPacket", Ref.forName(ReflectionUtil.getNMSPackage() + ".Packet").getClazz())
                .invoke(playerConnection, packet);
    }
}
