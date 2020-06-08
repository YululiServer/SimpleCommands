package xyz.acrylicstyle.simplecommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.acrylicstyle.tomeito_api.command.PlayerCommandExecutor;

public class PosCommand extends PlayerCommandExecutor {
    @Override
    public void onCommand(Player player, String[] args) {
        Location l = player.getLocation();
        player.sendMessage(ChatColor.GOLD + "現在の座標:");
        player.sendMessage(ChatColor.YELLOW + "X: " + ChatColor.RED + l.getBlockX());
        player.sendMessage(ChatColor.YELLOW + "Y: " + ChatColor.RED + l.getBlockY());
        player.sendMessage(ChatColor.YELLOW + "Z: " + ChatColor.RED + l.getBlockZ());
    }
}
