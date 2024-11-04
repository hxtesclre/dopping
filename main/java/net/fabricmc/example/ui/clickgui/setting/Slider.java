package net.fabricmc.example.ui.clickgui.setting;

import net.fabricmc.example.module.Setting.NumberSetting;
import net.fabricmc.example.module.Setting.Setting;
import net.fabricmc.example.ui.clickgui.ModuleButton;
import net.minecraft.client.util.math.MatrixStack;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Slider extends Component {

    public NumberSetting numSet = (NumberSetting)setting;
    public Slider(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.numSet = (NumberSetting) setting;
    }

    @Override
    public void render(MatrixStack matricies, int mouseX, int mouseY, float delta) {
        super.render(matricies, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        super.mouseReleased(mouseX, mouseY, button);
    }

    private double roundToPlace(double value, int place){
        if(place < 0 ){
            return value;
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(place, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
