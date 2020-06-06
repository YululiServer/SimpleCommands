package xyz.acrylicstyle.simplecommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.acrylicstyle.tomeito_api.command.PlayerCommandExecutor;

public class HatCommand extends PlayerCommandExecutor {
    @Override
    public void onCommand(Player player, String[] args) {
        ItemStack item = player.getInventory().getItemInMainHand().clone();
        if (item.getType() == Material.AIR) {
            player.sendMessage(ChatColor.RED + "This item cannot be used as hat.");
            return;
        }
        ItemStack helmet = player.getInventory().getHelmet();
        if (helmet != null && helmet.getType() != Material.AIR) player.getInventory().setItemInMainHand(helmet);
        player.getInventory().setHelmet(item);
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Enjoy your new hat!");
    }
}
