package net.fabricmc.example.ui.clickgui.setting;

import net.fabricmc.example.module.Setting.BooleanSetting;
import net.fabricmc.example.module.Setting.ModeSetting;
import net.fabricmc.example.module.Setting.Setting;
import net.fabricmc.example.ui.clickgui.ModuleButton;
import net.minecraft.client.util.math.MatrixStack;

public class ModeBox extends Component{

    private ModeSetting modeSet = (ModeSetting) setting;

    public ModeBox(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.modeSet = (ModeSetting) setting;
    }

    @Override
    public void render(MatrixStack matricies, int mouseX, int mouseY, float delta) {
        super.render(matricies, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
    }
}
