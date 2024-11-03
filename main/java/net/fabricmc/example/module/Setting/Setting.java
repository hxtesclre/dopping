package net.fabricmc.example.module.Setting;

public class Setting {

    private String name;
    private boolean visible = true;

    public Setting(String name){
        this.name = name;
    }

    public Setting(String name, boolean visible) {
        this.name = name;
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public boolean isVisible() {
        return visible;
    }
}
