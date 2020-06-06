package xyz.acrylicstyle.simplecommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.shared.BaseMojangAPI;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class FirstJoin implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/firstjoin <player>");
            return true;
        }
        UUID uuid = BaseMojangAPI.getUniqueId(args[0]);
        if (uuid == null) {
            sender.sendMessage(ChatColor.RED + "Please specify a valid player.");
            return true;
        }
        sender.sendMessage(ChatColor.GREEN + args[0] + " joined for the first time at " + DateFormat.getInstance().format(new Date(Bukkit.getOfflinePlayer(uuid).getFirstPlayed())));
        sender.sendMessage(ChatColor.GREEN + "Last login: " + DateFormat.getInstance().format(new Date(Bukkit.getOfflinePlayer(uuid).getLastLogin())));
        sender.sendMessage(ChatColor.GREEN + "Last seen: " + DateFormat.getInstance().format(new Date(Bukkit.getOfflinePlayer(uuid).getLastSeen())));
        return true;
    }
}
