package xyz.acrylicstyle.simplecommands.commands;

import net.minecraft.server.v1_15_R1.NetworkManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.simplecommands.utils.Utils;

public class Crash implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof ConsoleCommandSender)) return true;
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/crash <player>");
            return true;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Invalid player!");
            return true;
        }
        NetworkManager nm = ((CraftPlayer) player).getHandle().playerConnection.networkManager;
        for (int i = 0; i < 100; i++) nm.sendPacket(Utils.packet);
        return true;
    }
}
