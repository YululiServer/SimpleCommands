package xyz.acrylicstyle.simplecommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.simplecommands.utils.Utils;

public class PingAll implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Utils.getOnlinePlayers().forEach(player -> sender.sendMessage(ChatColor.GREEN + player.getName() + "'s ping: " + getPing(player) + "ms"));
        return true;
    }

    static String getPing(Player player) {
        int ping = player.spigot().getPing();
        String message;
        if (ping <= 5) message = "" + ChatColor.LIGHT_PURPLE + ping;
        else if (ping <= 50) message = "" + ChatColor.GREEN + ping;
        else if (ping <= 150) message = "" + ChatColor.YELLOW + ping;
        else if (ping <= 250) message = "" + ChatColor.GOLD + ping;
        else if (ping <= 350) message = "" + ChatColor.RED + ping;
        else message = "" + ChatColor.DARK_RED + ping;
        return message;
    }
}
