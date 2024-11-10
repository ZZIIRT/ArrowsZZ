package org.zzii.arrowszz.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zzii.arrowszz.ArrowsZZ;
import org.zzii.arrowszz.utils.ArrowUtils;

public class ArrowCommand implements CommandExecutor {

    private final ArrowsZZ plugin;

    public ArrowCommand(ArrowsZZ plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эта команда может быть выполнена только игроком.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("zarrows.give")) {
            player.sendMessage(ArrowUtils.colorize(plugin.getPluginConfig().getString("messages.no-permission")));
            return true;
        }

        if (args.length < 3 || !args[0].equalsIgnoreCase("give")) {
            sender.sendMessage(ArrowUtils.colorize(plugin.getPluginConfig().getString("messages.usage")));
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if (targetPlayer == null) {
            sender.sendMessage(ArrowUtils.colorize(plugin.getPluginConfig().getString("messages.player_not_found")));
            return true;
        }

        String arrowKey = args[2];
        if (!plugin.getPluginConfig().contains("arrows." + arrowKey)) {
            sender.sendMessage(ArrowUtils.colorize(plugin.getPluginConfig().getString("messages.arrow_not_found")));
            return true;
        }

        int amount = 1;
        if (args.length >= 4) {
            try {
                amount = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Указано неверное количество.");
                return true;
            }
        }

        ArrowUtils.giveArrow(plugin, targetPlayer, arrowKey, amount);
        sender.sendMessage(ArrowUtils.colorize(plugin.getPluginConfig().getString("messages.arrow_given_own")));
        targetPlayer.sendMessage(ArrowUtils.colorize(plugin.getPluginConfig().getString("messages.arrow_given")));
        return true;
    }
}
