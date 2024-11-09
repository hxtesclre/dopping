package net.fabricmc.example.module.Movement;

import net.fabricmc.example.module.Mod;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class NoFall extends Mod {
    public NoFall() {
        super("NoFall", "Prevents fall damage", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_N);
    }

    @Override
    public void onTick() {
        if (mc.player != null) {
            if (mc.player.fallDistance > 2.5 && !mc.player.isOnGround() && mc.player.getVelocity().y < 0) {
                Vec3d pos = mc.player.getPos();
                // Имитация приземления игрока путем отправки пакета с onGround установленным в true
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(pos.x, pos.y, pos.z, true));
                mc.player.fallDistance = 0; // Сбрасываем дистанцию падения
            }
        }
        super.onTick();
    }
}
