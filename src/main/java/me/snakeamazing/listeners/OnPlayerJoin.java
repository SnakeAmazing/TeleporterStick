package me.snakeamazing.listeners;

import me.snakeamazing.utils.FileManager;
import me.snakeamazing.utils.FileMatcher;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OnPlayerJoin implements Listener {

    private FileManager config;

    public OnPlayerJoin(FileMatcher matcher) {
        this.config = matcher.getFile("config");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (config.getBoolean("items.teleporter-stick.give-stick-on-join")) {

            ItemStack stickItem = new ItemStack(Material.STICK, 1);

            ItemMeta meta = stickItem.getItemMeta();

            meta.setDisplayName(config.getString("items.teleporter-stick.name"));

            meta.setLore(config.getStringList("items.teleporter-stick.lore"));

            meta.addEnchant(Enchantment.DURABILITY, 3, true);

            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            stickItem.setItemMeta(meta);

            player.getInventory().setItem(config.getInt("items.teleporter-stick.position"), stickItem);
        }
    }
}
