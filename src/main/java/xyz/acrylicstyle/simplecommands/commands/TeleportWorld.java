package xyz.acrylicstyle.simplecommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import util.CollectionList;
import util.ICollectionList;

public class TeleportWorld implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        @NotNull CollectionList<Player> players = ICollectionList.asList(Bukkit.getServer().selectEntities(sender, "@p")).map(e -> (Player) e);
        Player player = players.get(0);
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/teleportworld <world>");
            return true;
        }
        World world = Bukkit.getWorld(args[0]);
        if (world == null) {
            sender.sendMessage(ChatColor.RED + "正しいワールドを指定してください。");
            return true;
        }
        player.teleport(world.getSpawnLocation());
        return true;
    }
}
