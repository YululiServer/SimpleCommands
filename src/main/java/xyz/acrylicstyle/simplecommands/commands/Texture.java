package xyz.acrylicstyle.simplecommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import util.ICollectionList;
import xyz.acrylicstyle.simplecommands.SimpleCommands;
import xyz.acrylicstyle.simplecommands.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Texture implements CommandExecutor {
    public static Sound BLOCK_NOTE_PLING;

    static {
        if (ICollectionList.asList(Sound.values()).map(Enum::name).contains("BLOCK_NOTE_BLOCK_PLING")) {
            BLOCK_NOTE_PLING = Sound.valueOf("BLOCK_NOTE_BLOCK_PLING");
        } else if (ICollectionList.asList(Sound.values()).map(Enum::name).contains("BLOCK_NOTE_PLING")) {
            BLOCK_NOTE_PLING = Sound.valueOf("BLOCK_NOTE_PLING");
        } else {
            BLOCK_NOTE_PLING = Sound.valueOf("NOTE_PLING");
        }
    }

    public static final List<UUID> confirm = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be invoked from console.");
            return true;
        }
        if (args.length != 0 && args[0].equalsIgnoreCase("clear")) {
            ((Player) sender).setResourcePack("https://google.com", ""); // invalid url will clear resource pack
            return true;
        }
        String texture = SimpleCommands.instance.getConfig().getString("texture");
        if (texture == null) return true;
        Player player = (Player) sender;
        if (args.length != 0 && player.isOp() && args[0].equalsIgnoreCase("all")) {
            Utils.getOnlinePlayers().forEach(p -> p.setResourcePack(texture, "bruh"));
            return true;
        }
        if (confirm.contains(player.getUniqueId())) {
            player.setResourcePack(texture, "blah");
            player.sendMessage(ChatColor.GREEN + "リソースパックを適用しました。");
            player.sendMessage(ChatColor.GREEN + "元に戻す場合は" + ChatColor.YELLOW + "//texture clear" + ChatColor.GREEN + "を使用してください。");
            confirm.remove(player.getUniqueId());
        } else {
            player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "==============================");
            player.sendMessage("");
            player.sendMessage("" + ChatColor.GOLD  + ChatColor.BOLD + "(!) " + ChatColor.RESET + ChatColor.GOLD
                    + "リソースパックを読み込みますか？");
            player.sendMessage(ChatColor.GOLD + "読み込む場合は、10秒以内に"
                    + ChatColor.YELLOW + "//texture" + ChatColor.GOLD +"をもう一度打ってください。");
            player.sendMessage("");
            player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "==============================");
            player.playSound(player.getLocation(), BLOCK_NOTE_PLING, 100, 1);
            confirm.add(player.getUniqueId());
            new BukkitRunnable() {
                @Override
                public void run() {
                    confirm.remove(player.getUniqueId());
                }
            }.runTaskLater(SimpleCommands.instance, 20*10);
        }
        return true;
    }
}
