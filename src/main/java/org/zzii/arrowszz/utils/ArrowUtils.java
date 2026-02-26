package org.zzii.arrowszz.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.zzii.arrowszz.model.ArrowDefinition;

public class ArrowUtils {

    public static void giveArrow(Player player, ArrowDefinition arrow, int amount) {
        ItemStack item = new ItemStack(Material.TIPPED_ARROW, amount);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        if (meta == null) return;

        meta.setDisplayName(arrow.getDisplayName());
        meta.setLore(arrow.getLore());
        meta.setColor(arrow.getColor());

        if (arrow.isHideAttributes()) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ENCHANTS);
        }

        if (arrow.isGlow()) {
            meta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        arrow.getEffects().forEach(effect -> meta.addCustomEffect(effect, true));
        item.setItemMeta(meta);
        player.getInventory().addItem(item);
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
