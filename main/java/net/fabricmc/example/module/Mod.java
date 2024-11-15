package net.fabricmc.example.module;

import net.fabricmc.example.module.Setting.Setting;
import net.minecraft.client.MinecraftClient;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Mod {
    private String name;
    private String displayName;
    private String description;
    private int key;
    public Category category;
    private boolean enabled;

    private List<Setting> settings = new ArrayList<>();

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Mod(String name, String description, Category category) {
        this.name = name;
        this.displayName = name;
        this.description = description;
        this.category = category;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void addSetting(Setting setting){
        settings.add(setting);
    }

    public void addSettings(Setting...settings){
        for (Setting setting : settings) addSetting(setting);
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (enabled) onEnable();
        else onDisable();
    }

    public void onEnable() {
        // Реализация метода onEnable
    }

    public void onDisable() {
        // Реализация метода onDisable
    }

    public void onTick() {
        // Реализация метода onTick
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) onEnable();
        else onDisable();
    }

    public enum Category {
        COMBAT("Combat"),
        MOVEMENT("MOVEMENT"),
        RENDER("RENDER"),
        EXPLOIT("EXPLOIT"),
        WORLD("WORLD");

        public String name;

        private Category(String name){
            this.name = name;
        }

    }
}
