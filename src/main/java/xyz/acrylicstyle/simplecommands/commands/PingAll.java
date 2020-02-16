package xyz.acrylicstyle.simplecommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.acrylicstyle.simplecommands.utils.Utils;

import java.lang.reflect.InvocationTargetException;

public class PingAll implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Utils.getOnlinePlayers().forEach(player -> {
            try {
                sender.sendMessage(ChatColor.GREEN + player.getName() + "'s ping: " + getPing(player) + "ms");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return true;
    }

    static String getPing(Player player) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Object craftPlayer = Utils.getHandle(player);
        int ping = (int) craftPlayer.getClass().getField("ping").get(craftPlayer);
        String message;
        if (ping <= 5) message = "" + net.md_5.bungee.api.ChatColor.LIGHT_PURPLE + ping;
        else if (ping <= 50) message = "" + net.md_5.bungee.api.ChatColor.GREEN + ping;
        else if (ping <= 150) message = "" + net.md_5.bungee.api.ChatColor.YELLOW + ping;
        else if (ping <= 250) message = "" + net.md_5.bungee.api.ChatColor.GOLD + ping;
        else if (ping <= 350) message = "" + net.md_5.bungee.api.ChatColor.RED + ping;
        else message = "" + net.md_5.bungee.api.ChatColor.DARK_RED + ping;
        return message;
    }
}
