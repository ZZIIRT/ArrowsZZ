package org.zzii.arrowszz;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.zzii.arrowszz.command.ArrowCommand;

public final class ArrowsZZ extends JavaPlugin {

    private FileConfiguration pluginConfig;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadPluginConfig();
        registerCommands();
        getLogger().info("ArrowsZZ плагин включен!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ArrowsZZ плагин выключен!");
    }

    private void registerCommands() {
        ArrowCommand executor = new ArrowCommand(this);
        getCommand("zarrows").setExecutor(executor);
        getCommand("zarrows").setTabCompleter(new ArrowTabCompleter(this));
    }

    public FileConfiguration getPluginConfig() {
        return pluginConfig;
    }

    public void reloadPluginConfig() {
        reloadConfig();
        pluginConfig = getConfig();
    }
}
