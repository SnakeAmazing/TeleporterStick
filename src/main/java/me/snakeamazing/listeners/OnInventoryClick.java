package me.snakeamazing.listeners;

import me.snakeamazing.utils.FileManager;
import me.snakeamazing.utils.FileMatcher;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnInventoryClick implements Listener {

    private FileManager config;

    public OnInventoryClick(FileMatcher matcher){
        this.config = matcher.getFile("config");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){

      if (event.getSlot() >= 0 && event.getCurrentItem().getType() == Material.STICK) {

          event.setCancelled(true);
      }

    }
}
