package org.zzii.arrowszz;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ArrowTabCompleter implements TabCompleter {

    private final ArrowsZZ plugin;

    public ArrowTabCompleter(ArrowsZZ plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("give", "reload");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(OfflinePlayer::getName)
                    .collect(Collectors.toList());
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            ConfigurationSection section = plugin.getPluginConfig().getConfigurationSection("arrows");
            if (section == null) return Collections.emptyList();
            return section.getKeys(false).stream().collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
