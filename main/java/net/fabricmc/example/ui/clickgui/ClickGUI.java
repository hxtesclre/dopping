package net.fabricmc.example.ui.clickgui;

import net.fabricmc.example.module.Mod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends Screen {
    public static final ClickGUI INSTANCE = new ClickGUI();

    private List<Frame> frames;

    private ClickGUI() {
        super(Text.of("Click GUI"));

        frames = new ArrayList<>();

        int offset = 20;
        for (Mod.Category category : Mod.Category.values()){
            frames.add(new Frame(category, offset, 30, 100, 30 ));
            offset += 120;
        }

    }

    @Override
    public void render(MatrixStack matricies, int mouseX, int mouseY, float delta){
        for(Frame frame : frames){
            frame.render(matricies, mouseX, mouseY, delta);
            frame.updatePosition(mouseX, mouseY);
        }
        super.render(matricies, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for(Frame frame : frames){
            frame.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button){
        for(Frame frame : frames){
            frame.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

}

