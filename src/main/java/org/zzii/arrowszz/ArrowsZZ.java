package org.zzii.arrowszz;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.zzii.arrowszz.command.ArrowCommand;

public final class ArrowsZZ extends JavaPlugin {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        getLogger().info("ArrowsZZ плагин включен!");

        getCommand("zarrows").setExecutor(new ArrowCommand(this));
        getCommand("zarrows").setTabCompleter(new ArrowTabCompleter(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("ArrowsZZ плагин выключен!");
    }

    public FileConfiguration getPluginConfig() {
        return config;
    }
}
