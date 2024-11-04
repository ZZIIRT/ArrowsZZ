package org.zzii.arrowszz;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
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
            return Arrays.asList("give");
        } else if (args.length == 2) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        } else if (args.length == 3) {
            return new ArrayList<>(plugin.getPluginConfig().getConfigurationSection("arrows").getKeys(false));
        }
        return null;
    }
}
