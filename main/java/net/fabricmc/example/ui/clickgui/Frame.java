package net.fabricmc.example.ui.clickgui;

import net.fabricmc.example.module.Mod;
import net.fabricmc.example.module.ModuleManager;
import net.fabricmc.example.ui.clickgui.setting.Component;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frame {
    public int x, y, width, height, dragX, dragY;
    public Mod.Category category;
    public boolean dragging, extended;

    private List<ModuleButton> buttons;

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Frame(Mod.Category category, int x, int y, int width, int height){
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragging = false;
        this.extended = false;

        buttons = new ArrayList<>();

        int offset = height;
        for (Mod mod : ModuleManager.INSTANCE.getModulesInCategory(category)){
            buttons.add(new ModuleButton(mod, this, offset));
            offset += height;
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta){
        DrawableHelper.fill(matrices, x, y, x + width, y + height, Color.red.getRGB());

        int textOffset = ((height / 2) - mc.textRenderer.fontHeight / 2);
        mc.textRenderer.drawWithShadow(matrices, category.name, x + textOffset, y + textOffset, -1);
        mc.textRenderer.drawWithShadow(matrices, extended ? "-" : "+", x + width - textOffset - 2 - mc.textRenderer.getWidth("+"), y + textOffset, -1);

        if(extended) {
            for (ModuleButton button : buttons) {
                button.render(matrices, mouseX, mouseY, delta);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button){
        if(isHovered(mouseX, mouseY)){
            if (button == 0) {
                dragging = true;
                dragX = (int) (mouseX - x);
                dragY = (int) (mouseY - y);
            } else if (button == 1){
                extended = !extended;
                updateButton(); // Обновляем кнопки при изменении состояния extended
            }
        }

        if(extended) {
            for (ModuleButton mb : buttons) {
                mb.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button){
        if(button == 0 && dragging) dragging = false;

        for(ModuleButton mb : buttons){
            mb.mouseReleased(mouseX, mouseY, button);
        }
    }

    public boolean isHovered(double mouseX, double mouseY){
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void updatePosition(double mouseX, double mouseY){
        if(dragging) {
            x = (int) (mouseX - dragX);
            y = (int) (mouseY - dragY);
        }
    }

    public void updateButton() {
        int offset = height;

        for (ModuleButton button : buttons){
            button.offset = offset; // Исправлено: используем offset
            offset += height;

            if (button.extended){
                for (Component component : button.components){
                    if (component.setting.isVisible()) offset += height;
                }
            }
        }
    }

    public MinecraftClient getMc() {
        return mc;
    }
}
