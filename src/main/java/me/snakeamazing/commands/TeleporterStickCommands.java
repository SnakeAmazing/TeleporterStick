package me.snakeamazing.commands;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;
import me.snakeamazing.TeleporterStick;
import me.snakeamazing.utils.FileManager;
import me.snakeamazing.utils.FileMatcher;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.logging.Level;


@ACommand(names = {"teleporterstick", "ts", "stick", "tpstick"}, permission = "tpstick.admin")
@Usage(usage = "§8- §eGet or give the stick to a player")
public class TeleporterStickCommands implements CommandClass {

    private FileManager config;
    private TeleporterStick plugin;

    public TeleporterStickCommands(FileMatcher matcher, TeleporterStick plugin) {
        this.config = matcher.getFile("config");
        this.plugin = plugin;
    }

    @ACommand(names = "", permission = "tpstick.admin")
    public boolean mainCommand(@Injected(true) CommandSender sender){
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED +"You must be a player");
            return true;
        }

        Player player = (Player) sender;

        ItemStack stickItem = new ItemStack(Material.STICK, 1);

        ItemMeta meta = stickItem.getItemMeta();

        meta.setDisplayName(config.getString("items.teleporter-stick.name"));

        meta.setLore(config.getStringList("items.teleporter-stick.lore"));

        meta.addEnchant(Enchantment.DURABILITY, 3, true);

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        stickItem.setItemMeta(meta);

        player.getInventory().setItem(config.getInt("items.teleporter-stick.position"), stickItem);

        return true;
    }

    @ACommand(names = "give", permission = "tpstick.admin")
    @Usage(usage = "Use this command to give the stick to a player")
    public boolean giveToPlayerCommand(@Injected(true) CommandSender sender, OfflinePlayer target){

        if (sender instanceof Player) {
            sender.sendMessage("You must be a player to run this command!");
        }
        Player player = (Player) sender;

        if (target == null) {
            player.sendMessage(config.getString("messages.player-not-found"));

            return true;
        }

        if (target.isOnline()){

            ItemStack stickItem = new ItemStack(Material.STICK, 1);

            ItemMeta meta = stickItem.getItemMeta();

            meta.setDisplayName(config.getString("items.teleporter-stick.name"));

            meta.setLore(config.getStringList("items.teleporter-stick.lore"));

            meta.addEnchant(Enchantment.DURABILITY, 3, true);

            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            stickItem.setItemMeta(meta);

            target.getPlayer().getInventory().setItem(config.getInt("items.teleporter-stick.position"), stickItem);

        } else {
            player.sendMessage(config.getString("messages.player-not-found"));
        }

        return true;
    }

    @ACommand(names = "reload", permission = "tpstick.admin")
    public boolean reload(@Injected(true) CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Reloading TeleporterStick config!");
        try {
            config.reload();
            sender.sendMessage(ChatColor.GREEN + "Successfully reloaded the TeleporterStick config!");

            return true;
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().log(Level.SEVERE, "An exception ocurred while reloading the config file");
            sender.sendMessage(ChatColor.RED + "Error while reloading the config");

            return true;
        }
    }
}
