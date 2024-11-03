package net.fabricmc.example.module.Movement;

import net.fabricmc.example.module.Mod;
import org.lwjgl.glfw.GLFW;

public class Flight extends Mod {
    public Flight() {
        super("Flight", "Allows you to fly", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_G);
    }

    @Override
    public void onTick() {
        mc.player.abilities.allowFlying = true;
        super.onTick();
    }

    @Override
    public void onDisable() {
        mc.player.abilities.allowFlying = false;
        super.onDisable();
    }
}
