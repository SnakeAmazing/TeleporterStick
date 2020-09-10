package me.snakeamazing.utils;


import me.snakeamazing.TeleporterStick;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class FileMatcher {

    private final JavaPlugin plugin = JavaPlugin.getPlugin(TeleporterStick.class);

    private final Map<String, FileManager> files = new HashMap<>();

    {
        files.put("config", new FileManager(plugin, "config"));

    }

    public FileManager getFile(String key) {
        return files.get(key);
    }
}
