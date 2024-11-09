package net.fabricmc.example.module.Movement;

import net.fabricmc.example.module.Mod;
import net.fabricmc.example.module.Setting.BooleanSetting;
import net.fabricmc.example.module.Setting.ModeSetting;
import net.fabricmc.example.module.Setting.NumberSetting;
import org.lwjgl.glfw.GLFW;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

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
        if (mc.player != null) {
            mc.player.abilities.allowFlying = true;
            mc.player.abilities.setFlySpeed(speed.getValueFloat());

            // Добавляем обработку пакетов для предотвращения урона при приземлении
            if (mc.player.fallDistance > 2.5 && !mc.player.isOnGround()) {
                double x = mc.player.getX();
                double y = mc.player.getY();
                double z = mc.player.getZ();
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(x, y - 0.1, z, true));
            }
        }
        super.onTick();
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.abilities.allowFlying = false;
            mc.player.abilities.flying = false;
        }
        super.onDisable();
    }
}
