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
        this.category = category; // Не забываем инициализировать category
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


    public void render(MatrixStack matricies, int mouseX, int mouseY, float delta){
       DrawableHelper.fill(matricies, x, y, x + width, y + height, Color.red.getRGB());

       int offset = ((height / 2) - mc.textRenderer.fontHeight / 2);
       mc.textRenderer.drawWithShadow(matricies, category.name, x + offset, y + offset, -1);
       mc.textRenderer.drawWithShadow(matricies, extended ? "-" : "+", x + width - offset - 2 - mc.textRenderer.getWidth("+"), y + offset, -1);

       if(extended) {
           for (ModuleButton button : buttons) {
               button.render(matricies, mouseX, mouseY, delta);
           }
       }
   }

   public void mouseClicked(double mouseX, double mouseY, int button){
        if(isHovered(mouseX, mouseY) && button == 0){
            if (button == 0) {
                dragging = true;
                dragX = (int) (mouseX - x);
                dragY = (int) (mouseY - y);
            } else if (button == 1){
                extended = !extended;
            }
        }

        if(extended) {
            for (ModuleButton mb : buttons) {
                mb.mouseClicked(mouseX, mouseY, button);
            }
        }

   }

   public void mouseReleased(double mouseX,  double mouseY, int button){
        if(button == 0 && dragging == true) dragging = false;
   }

    public MinecraftClient getMc() {
        return mc;
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
            button.offset = height;
            offset += height;

            if (button.extended){
                for (Component component : button.components){
                    if (component.setting.isVisible()) offset += height;
                }
            }
        }
    }
}
