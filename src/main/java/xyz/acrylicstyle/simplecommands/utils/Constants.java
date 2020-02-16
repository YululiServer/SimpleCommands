package xyz.acrylicstyle.simplecommands.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import util.StringCollection;

import java.util.UUID;

public final class Constants {
    private Constants() {}

    public static final StringCollection<ItemStack> items = new StringCollection<>();

    static {
        ItemStack yululi_head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta yululiMeta = (SkullMeta) yululi_head.getItemMeta();
        assert yululiMeta != null;
        yululiMeta.setDisplayName(ChatColor.AQUA + "yululi の頭");
        yululiMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        yululiMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString("9f317138-f088-4480-babb-fc91a6d0a5a8")));
        yululi_head.setItemMeta(yululiMeta);
        items.add("minecraft:yululi_head", yululi_head);
    }
}
