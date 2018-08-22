package com.bytekangaroo.hotsauce;

import com.bytekangaroo.hotsauce.command.BaseCommand;
import com.bytekangaroo.hotsauce.listeners.BlockSpreadListener;
import com.bytekangaroo.hotsauce.listeners.PlayerMoveListener;
import com.bytekangaroo.hotsauce.listeners.PlayerQuitListener;
import com.bytekangaroo.hotsauce.listeners.PlayerTeleportListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    // PLUGIN DESCRIPTION
    private String prefix = "§c[§7HotSauce§c]§7 ";

    private static Main main;

    @Override
    public void onEnable() {
        // Get the plugin manager
        PluginManager pm = Bukkit.getServer().getPluginManager();

        // Create configuration
        createConfig();

        // Register the Main instance
        main = this;

        // Register events
        Bukkit.getServer().getPluginManager().registerEvents(new BlockSpreadListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerTeleportListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);

        // Register commands
        getCommand("hotsauce").setExecutor(new BaseCommand());

        getLogger().log(Level.INFO, "HotSauce has successfully been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "HotSauce has successfully been disabled!");
    }

    public void createConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            getLogger().log(Level.INFO, "No configuration found for HotSauce " + getDescription().getVersion());
            saveDefaultConfig();
        } else {
            getLogger().log(Level.INFO, "Configuration found for HotSauce v" + getDescription().getVersion() + "!");
        }
    }

    public static Main getInstance() {
        return main;
    }

    public String getPrefix() {
        return prefix;
    }
}