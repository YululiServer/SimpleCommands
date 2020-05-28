package xyz.acrylicstyle.simplecommands;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.acrylicstyle.simplecommands.commands.*;

import java.util.List;
import java.util.Objects;

public class SimpleCommands extends JavaPlugin implements Listener {
    public static SimpleCommands instance = null;
    private List<String> disabledCommands;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        disabledCommands = this.getConfig().getStringList("disabledCommands");
        if (!disabledCommands.contains("pingall")) Objects.requireNonNull(Bukkit.getPluginCommand("pingall")).setExecutor(new PingAll());
        if (!disabledCommands.contains("suicide")) Objects.requireNonNull(Bukkit.getPluginCommand("suicide")).setExecutor(new Suicide());
        if (!disabledCommands.contains("teleportworld")) Objects.requireNonNull(Bukkit.getPluginCommand("teleportworld")).setExecutor(new TeleportWorld());
        Objects.requireNonNull(Bukkit.getPluginCommand("crash")).setExecutor(new Crash());
        Objects.requireNonNull(Bukkit.getPluginCommand("textures")).setExecutor(new Texture());
        Bukkit.getPluginManager().registerEvents(this, this);
        for (Player p : Bukkit.getOnlinePlayers()) onPlayerJoin(new PlayerJoinEvent(p, null));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer().isOp()) e.getPlayer().setPlayerListName(ChatColor.RED + "[★]" + ChatColor.WHITE + e.getPlayer().getName());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getWhoClicked().getGameMode() == GameMode.CREATIVE) return;
        if (e.getClickedInventory() == null) return;
        if ((
                e.getInventory().getType() == InventoryType.ENDER_CHEST
                        || e.getInventory().getType() == InventoryType.CRAFTING
                        || e.getInventory().getType() == InventoryType.ANVIL
        ) && (
                e.getClickedInventory().getType() == InventoryType.ENDER_CHEST
                        || e.getClickedInventory().getType() == InventoryType.PLAYER
                        || e.getClickedInventory().getType() == InventoryType.ANVIL
        )) return;
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR && !disabledCommands.contains("binding_curse")) {
            if (Objects.requireNonNull(e.getCurrentItem().getItemMeta()).hasEnchant(Enchantment.BINDING_CURSE)) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        ItemStack item = e.getItemInHand();
        if (item.getType() != Material.AIR && !disabledCommands.contains("binding_curse")) {
            if (Objects.requireNonNull(item.getItemMeta()).hasEnchant(Enchantment.BINDING_CURSE)) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        ItemStack item = e.getItemDrop().getItemStack();
        if (item.getType() != Material.AIR && !disabledCommands.contains("binding_curse")) {
            if (Objects.requireNonNull(item.getItemMeta()).hasEnchant(Enchantment.BINDING_CURSE)) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!disabledCommands.contains("end_portal_fix")) {
            if (e.getTo().getBlock().getType() == Material.END_PORTAL) {
                if (e.getPlayer().getWorld().getEnvironment() == World.Environment.NORMAL)
                    e.getPlayer().teleport(Objects.requireNonNull(Bukkit.getWorld("world_the_end")).getSpawnLocation());
                else if (e.getPlayer().getWorld().getEnvironment() == World.Environment.THE_END && e.getPlayer().getBedSpawnLocation() == null)
                    e.getPlayer().teleport(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation());
            }
        }
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent e) {
        ItemStack left = e.getInventory().getItem(0);
        ItemStack right = e.getInventory().getItem(1);
        ItemStack result = e.getResult();
        if (left != null && right != null) {
            if (left.getType() == Material.BOW) {
                if (right.getType() == Material.ENCHANTED_BOOK) {
                    EnchantmentStorageMeta meta = (EnchantmentStorageMeta) right.getItemMeta();
                    assert meta != null;
                    if (meta.hasStoredEnchant(Enchantment.MENDING)) {
                        ItemStack result2 = result == null || result.getType() == Material.AIR ? left.clone() : result.clone();
                        ItemMeta resultMeta = result2.getItemMeta();
                        assert resultMeta != null;
                        resultMeta.addEnchant(Enchantment.MENDING, 1, false);
                        result2.setItemMeta(resultMeta);
                        e.setResult(result2);
                    } else if (meta.hasStoredEnchant(Enchantment.ARROW_INFINITE)) {
                        ItemStack result2 = result == null || result.getType() == Material.AIR ? left.clone() : result.clone();
                        ItemMeta resultMeta = result2.getItemMeta();
                        assert resultMeta != null;
                        resultMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
                        result2.setItemMeta(resultMeta);
                        e.setResult(result2);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() != EntityType.PLAYER) return;
        Player damager = (Player) e.getDamager();
        if (damager.getName().equals("_")) {
            e.setCancelled(true);
            damager.setFireTicks(e.getEntity().getFireTicks());
            damager.damage(e.getFinalDamage()*2);
            damager.setVelocity(e.getEntity().getVelocity().multiply(-1));
            return;
        }
        ItemStack item = damager.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        if (!meta.hasDisplayName()) return;
        if (meta.getDisplayName().contains("Self-harm sword")) {
            e.setCancelled(true);
            damager.setFireTicks(e.getEntity().getFireTicks());
            damager.damage(e.getFinalDamage());
            damager.setVelocity(e.getEntity().getVelocity().multiply(-1));
        }
    }
}
