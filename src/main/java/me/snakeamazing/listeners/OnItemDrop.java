package me.snakeamazing.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class OnItemDrop implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getType() == Material.STICK) {
            event.setCancelled(true);
        }
    }
}
