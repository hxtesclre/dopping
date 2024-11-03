package net.fabricmc.example.ui.clickgui;

import net.fabricmc.example.module.Mod;
import net.minecraft.client.util.math.MatrixStack;

public class ModuleButton {

    public Mod module;
    public Frame parent;
    public int offset;

    public ModuleButton(Mod module, Frame parent, int offset){
        this.module = module;
        this.parent = parent;
        this.offset = offset;
    }

    public void render(MatrixStack matricies, int mouseX, int mouseY, float delta){

    }

    public void mouseClicked(double mouseX, double mouseY, int button){

    }

    public boolean isHovered(double mouseX, double mouseY){
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
    }


}
