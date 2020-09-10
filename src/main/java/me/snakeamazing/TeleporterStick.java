package me.snakeamazing;

import me.fixeddev.ebcm.Command;
import me.fixeddev.ebcm.CommandManager;
import me.fixeddev.ebcm.SimpleCommandManager;
import me.fixeddev.ebcm.bukkit.BukkitAuthorizer;
import me.fixeddev.ebcm.bukkit.BukkitCommandManager;
import me.fixeddev.ebcm.bukkit.BukkitMessenger;
import me.fixeddev.ebcm.bukkit.parameter.provider.BukkitModule;
import me.fixeddev.ebcm.parameter.provider.ParameterProviderRegistry;
import me.fixeddev.ebcm.parametric.ParametricCommandBuilder;
import me.fixeddev.ebcm.parametric.ReflectionParametricCommandBuilder;
import me.snakeamazing.commands.TeleporterStickCommands;
import me.snakeamazing.listeners.*;
import me.snakeamazing.utils.FileMatcher;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class TeleporterStick extends JavaPlugin {

    private FileMatcher fileMatcher;

    private ParametricCommandBuilder commandBuilder;
    private ParameterProviderRegistry registry;
    private CommandManager commandManager;

    private ItemStack stickItem;

    public void onEnable() {
        saveDefaultConfig();
        fileMatcher = new FileMatcher();
        registerCommands();
        registerEvents();
    }

    public void onDisable(){

    }

    public void registerEvents(){
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new OnPlayerInteract(fileMatcher, this), this);
        manager.registerEvents(new OnInventoryClick(fileMatcher), this);
        manager.registerEvents(new OnItemDrop(), this);
        manager.registerEvents(new OnPlayerJoin(fileMatcher), this);
        manager.registerEvents(new FallDamage(), this);
    }

    public void registerCommands(){
        registry = ParameterProviderRegistry.createRegistry();

        commandManager = new SimpleCommandManager(new BukkitAuthorizer(), new BukkitMessenger(), registry);
        commandManager = new BukkitCommandManager(commandManager, getName());

        registry.installModule(new BukkitModule());

        commandBuilder = new ReflectionParametricCommandBuilder();

        List<Command> commands = commandBuilder.fromClass(new TeleporterStickCommands(fileMatcher, this));

        commandManager.registerCommands(commands);
    }
}
