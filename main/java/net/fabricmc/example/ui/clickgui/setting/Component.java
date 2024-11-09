package net.fabricmc.example.ui.clickgui.setting;

import net.fabricmc.example.module.Setting.Setting;
import net.fabricmc.example.ui.clickgui.ModuleButton;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class Component {

    public Setting setting;
    public ModuleButton parent;
    public int offset;

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Component(Setting setting, ModuleButton parent, int offset) {
        this.setting = setting;
        this.parent = parent;
        this.offset = offset;
    }

    public void render(MatrixStack matricies, int mouseX, int mouseY, float delta) {
        // Implement rendering logic here
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        // Implement click handling here
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        // Implement mouse release handling here
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.parent.x && mouseX < parent.parent.x + parent.parent.width && mouseY > parent.parent.y + parent.offset + offset && mouseY < parent.parent.y + parent.offset + offset + parent.parent.height;
    }
}
