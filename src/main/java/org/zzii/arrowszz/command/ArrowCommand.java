package org.zzii.arrowszz.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zzii.arrowszz.ArrowsZZ;
import org.zzii.arrowszz.model.ArrowDefinition;
import org.zzii.arrowszz.utils.ArrowUtils;

public class ArrowCommand implements CommandExecutor {

    private final ArrowsZZ plugin;

    public ArrowCommand(ArrowsZZ plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(msg("usage"));
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            return handleReload(sender);
        }
        if (args[0].equalsIgnoreCase("give")) {
            return handleGive(sender, args);
        }
        sender.sendMessage(msg("usage"));
        return true;
    }

    private boolean handleReload(CommandSender sender) {
        if (!sender.hasPermission("zarrows.reload")) {
            sender.sendMessage(msg("no-permission"));
            return true;
        }
        plugin.reloadPluginConfig();
        sender.sendMessage(msg("reload"));
        return true;
    }

    private boolean handleGive(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(msg("usage"));
            return true;
        }
        if (!sender.hasPermission("zarrows.give")) {
            sender.sendMessage(msg("no-permission"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(msg("player_not_found"));
            return true;
        }
        String arrowKey = args[2];
        if (!plugin.getPluginConfig().contains("arrows." + arrowKey)) {
            sender.sendMessage(msg("arrow_not_found"));
            return true;
        }
        int amount = parseAmount(sender, args);
        if (amount < 0) return true;

        ArrowDefinition arrow = ArrowDefinition.fromConfig(plugin.getPluginConfig(), arrowKey);
        ArrowUtils.giveArrow(target, arrow, amount);
        notifySender(sender, target, amount);
        notifyTarget(target);
        return true;
    }

    private int parseAmount(CommandSender sender, String[] args) {
        if (args.length < 4) return 1;
        try {
            return Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Указано неверное количество.");
            return -1;
        }
    }

    private void notifySender(CommandSender sender, Player target, int amount) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.GREEN + "Вы выдали " + amount + " стрел(у) игроку " + target.getName() + ".");
            return;
        }
        String message = plugin.getPluginConfig().getString("messages.arrow_given_own", "");
        if (!message.isEmpty()) {
            sender.sendMessage(ArrowUtils.colorize(message));
        }
    }

    private void notifyTarget(Player target) {
        String message = plugin.getPluginConfig().getString("messages.arrow_given", "");
        if (!message.isEmpty()) {
            target.sendMessage(ArrowUtils.colorize(message));
        }
    }

    private String msg(String key) {
        return ArrowUtils.colorize(plugin.getPluginConfig().getString("messages." + key, ""));
    }
}
