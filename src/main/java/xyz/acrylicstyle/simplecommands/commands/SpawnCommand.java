package xyz.acrylicstyle.simplecommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.acrylicstyle.tomeito_api.command.PlayerCommandExecutor;

public class SpawnCommand extends PlayerCommandExecutor {
    @Override
    public void onCommand(Player player, String[] args) {
        if (!player.hasPermission("simplecommands.spawn") && !player.getWorld().getName().equalsIgnoreCase("world")) {
            player.sendMessage(ChatColor.RED + "このワールドで使用できません。");
            return;
        }
        player.teleport(player.getWorld().getSpawnLocation());
    }
}
