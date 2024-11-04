package net.fabricmc.example.module.Movement;

import net.fabricmc.example.module.Mod;
import net.fabricmc.example.module.Setting.BooleanSetting;
import net.fabricmc.example.module.Setting.ModeSetting;
import net.fabricmc.example.module.Setting.NumberSetting;
import org.lwjgl.glfw.GLFW;

public class Flight extends Mod {

    public NumberSetting speed = new NumberSetting("Speed", 0, 10, 1, 0.1);
    public BooleanSetting testBool = new BooleanSetting("Check", true);
    public ModeSetting testMode = new ModeSetting("Mode", "Test", "Test", "Test2", "Test3");

    public Flight() {
        super("Flight", "Allows you to fly", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_G);
        addSettings(speed, testBool, testMode);
    }

    @Override
    public void onTick() {
        mc.player.abilities.allowFlying = true;
        super.onTick();
        mc.player.abilities.setFlySpeed(speed.getValueFloat());
    }

    @Override
    public void onDisable() {
        mc.player.abilities.allowFlying = false;
        super.onDisable();
    }
}
