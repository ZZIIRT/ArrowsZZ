package org.zzii.arrowszz.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.zzii.arrowszz.ArrowsZZ;

import java.util.List;
import java.util.stream.Collectors;

public class ArrowUtils {

    public static void giveArrow(ArrowsZZ plugin, Player player, String arrowKey, int amount) {
        FileConfiguration config = plugin.getPluginConfig();
        String displayName = colorize(config.getString("arrows." + arrowKey + ".display-name", ""));
        List<String> lore = config.getStringList("arrows." + arrowKey + ".lore").stream()
                .map(ArrowUtils::colorize).collect(Collectors.toList());

        ItemStack arrow = new ItemStack(Material.TIPPED_ARROW, amount);
        PotionMeta meta = (PotionMeta) arrow.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            meta.setLore(lore);

            if (config.getBoolean("arrows." + arrowKey + ".hideattributes")) {
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ENCHANTS);
            }

            meta.addEnchant(Enchantment.ARROW_FIRE, 1, true);

            String colorString = config.getString("arrows." + arrowKey + ".color", "255,255,255");
            String[] rgb = colorString.split(",");
            int red = Integer.parseInt(rgb[0].trim());
            int green = Integer.parseInt(rgb[1].trim());
            int blue = Integer.parseInt(rgb[2].trim());
            meta.setColor(Color.fromRGB(red, green, blue));

            if (config.contains("arrows." + arrowKey + ".effects")) {
                config.getStringList("arrows." + arrowKey + ".effects").forEach(effectString -> {
                    String[] parts = effectString.split(",");
                    if (parts.length == 3) {
                        PotionEffectType type = PotionEffectType.getByName(parts[0].trim().toUpperCase());
                        int duration = Integer.parseInt(parts[1].trim());
                        int amplifier = Integer.parseInt(parts[2].trim());
                        if (type != null) {
                            meta.addCustomEffect(new PotionEffect(type, duration, amplifier), true);
                        }
                    }
                });
            }
            arrow.setItemMeta(meta);
        }
        player.getInventory().addItem(arrow);
    }

    public static String colorize(String message) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
    }
}
