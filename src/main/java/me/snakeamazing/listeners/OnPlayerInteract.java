package me.snakeamazing.listeners;

import me.snakeamazing.TeleporterStick;
import me.snakeamazing.utils.FileManager;
import me.snakeamazing.utils.FileMatcher;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class OnPlayerInteract implements Listener {

    private FileManager config;
    private TeleporterStick plugin;

    public OnPlayerInteract(FileMatcher matcher, TeleporterStick plugin){
        this.config = matcher.getFile("config");
        this.plugin = plugin;
    }

    public HashMap<UUID, Long> cooldownTimer = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){

        Player player = event.getPlayer();
        if (player.getItemInHand().getType() == Material.STICK && event.getAction() == Action.RIGHT_CLICK_AIR) {

            boolean block = player.getWorld().getBlockAt(player.getEyeLocation().getBlock().getLocation()).getType().isSolid();


            if (cooldownTimer.containsKey(player.getUniqueId())) {
                player.sendMessage(config.getString("messages.delay-message"));

            } else {
                if (block) {
                    player.sendMessage(config.getString("messages.prohibed"));

                } else {
                    Location loc = player.getLocation();
                    Vector vec = loc.getDirection();
                    vec.normalize();
                    vec.multiply(config.getInt("items.teleporter-stick.blocks-to-teleport"));
                    loc.add(vec);

                    if (!(loc.getBlock().getType().isSolid()) || loc.getBlock().getType().isTransparent()){
                        player.teleport(loc);
                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                        player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, 50 ,8);
                        player.getWorld().playEffect(player.getEyeLocation(), Effect.INSTANT_SPELL, 50, 8);
                        cooldownTimer.put(player.getUniqueId(), System.currentTimeMillis());
                        cooldown(3, player);
                    } else {
                        player.sendMessage(config.getString("messages.prohibed"));
                    }
                }
            }
        }
    }
    public void cooldown(int seconds, Player player) {
        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            cooldownTimer.remove(player.getUniqueId());
        }, seconds * 20);
    }
}
