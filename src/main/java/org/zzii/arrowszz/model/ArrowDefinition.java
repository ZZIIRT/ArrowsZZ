package org.zzii.arrowszz.model;

import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.zzii.arrowszz.utils.ArrowUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArrowDefinition {

    private final String displayName;
    private final List<String> lore;
    private final Color color;
    private final List<PotionEffect> effects;
    private final boolean glow;
    private final boolean hideAttributes;

    private ArrowDefinition(String displayName, List<String> lore, Color color,
                            List<PotionEffect> effects, boolean glow, boolean hideAttributes) {
        this.displayName = displayName;
        this.lore = lore;
        this.color = color;
        this.effects = effects;
        this.glow = glow;
        this.hideAttributes = hideAttributes;
    }

    public static ArrowDefinition fromConfig(FileConfiguration config, String key) {
        String path = "arrows." + key + ".";
        String displayName = ArrowUtils.colorize(config.getString(path + "display-name", ""));
        List<String> lore = config.getStringList(path + "lore").stream()
                .map(ArrowUtils::colorize)
                .collect(Collectors.toList());
        Color color = parseColor(config.getString(path + "color", "255,255,255"));
        List<PotionEffect> effects = parseEffects(config.getStringList(path + "effects"));
        boolean glow = config.getBoolean(path + "glow", true);
        boolean hideAttributes = config.getBoolean(path + "hideattributes", false);
        return new ArrowDefinition(displayName, lore, color, effects, glow, hideAttributes);
    }

    private static Color parseColor(String colorString) {
        String[] rgb = colorString.split(",");
        int red = Integer.parseInt(rgb[0].trim());
        int green = Integer.parseInt(rgb[1].trim());
        int blue = Integer.parseInt(rgb[2].trim());
        return Color.fromRGB(red, green, blue);
    }

    private static List<PotionEffect> parseEffects(List<String> effectStrings) {
        return effectStrings.stream()
                .filter(s -> s.split(",").length == 3)
                .map(s -> {
                    String[] parts = s.split(",");
                    PotionEffectType type = PotionEffectType.getByName(parts[0].trim().toUpperCase());
                    int duration = Integer.parseInt(parts[1].trim());
                    int amplifier = Integer.parseInt(parts[2].trim());
                    return type != null ? new PotionEffect(type, duration, amplifier) : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public String getDisplayName() { return displayName; }
    public List<String> getLore() { return lore; }
    public Color getColor() { return color; }
    public List<PotionEffect> getEffects() { return effects; }
    public boolean isGlow() { return glow; }
    public boolean isHideAttributes() { return hideAttributes; }
}
