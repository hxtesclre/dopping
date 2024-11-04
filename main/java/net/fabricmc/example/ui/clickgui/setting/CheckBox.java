package net.fabricmc.example.ui.clickgui.setting;

import net.fabricmc.example.module.Setting.BooleanSetting;
import net.fabricmc.example.ui.clickgui.ModuleButton;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class CheckBox extends Component {

    private BooleanSetting boolSet;

    public CheckBox(BooleanSetting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.boolSet = setting;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + offset, parent.parent.x + parent.parent.width, parent.parent.y + offset + parent.parent.height, new Color(0, 0, 0, 160).getRGB());
        if (isHovered(mouseX, mouseY)) {
            DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + offset, parent.parent.x + parent.parent.width, parent.parent.y + offset + parent.parent.height, new Color(0, 0, 0, 200).getRGB());
        }
        int textOffset = (parent.parent.height / 2 - parent.parent.getMc().textRenderer.fontHeight / 2);
        parent.parent.getMc().textRenderer.drawWithShadow(matrices, boolSet.getName(), parent.parent.x + textOffset, parent.parent.y + offset + textOffset, boolSet.isEnabled() ? Color.GREEN.getRGB() : -1);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            boolSet.setEnabled(!boolSet.isEnabled());
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.parent.x && mouseX < parent.parent.x + parent.parent.width && mouseY > parent.parent.y + offset && mouseY < parent.parent.y + offset + parent.parent.height;
    }
}
