package net.fabricmc.example.ui.clickgui;

import net.fabricmc.example.module.Mod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class Frame {
    public int x, y, width, height, dragX, dragY;
    public Mod.Category category;
    public boolean dragging;

    private MinecraftClient mc = MinecraftClient.getInstance();

    public Frame(Mod.Category category, int x, int y, int width, int height){
        this.category = category; // Не забываем инициализировать category
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragging = false;
    }


    public void render(MatrixStack matricies, int mouseX, int mouseY, float delta){
       DrawableHelper.fill(matricies, x, y, x + width, y + height, Color.red.getRGB());
       mc.textRenderer.drawWithShadow(matricies, category.name, x+2, y+2, -1);

   }

   public void mouseClicked(double mouseX, double mouseY, int button){
        if(isHovered(mouseX, mouseY) && button == 0){
            dragging = true;
            dragX = (int) (mouseX - x);
            dragY = (int) (mouseY - y);
        }
   }

   public void mouseReleased(double mouseX,  double mouseY, int button){
        if(button == 0 && dragging == true) dragging = false;
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

}
