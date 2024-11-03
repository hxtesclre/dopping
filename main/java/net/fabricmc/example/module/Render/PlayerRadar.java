package net.fabricmc.example.module.Render;

import net.fabricmc.example.module.Mod;
import net.fabricmc.example.module.Wrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class PlayerRadar extends Mod {
    public PlayerRadar() {
        super("PlayerRadar", "Allows you to see near players", Category.RENDER);
        this.setKey(GLFW.GLFW_KEY_P);
    }

    private void onRenderGameOverlay(MatrixStack matrices, float tickDelta) {
        MinecraftClient client = Wrapper.INSTANCE;
        if (client.world != null && client.player != null) {
            int y = 100; // Измените начальное значение Y
            for (PlayerEntity e : Wrapper.getPlayersList()) {
                if (e != client.player) {
                    float range = client.player.distanceTo(e);
                    float health = e.getHealth();

                    String healthStr = health >= 12.0 ? String.format(Formatting.GREEN + "[%.1f]", health) :
                            health >= 4.0 ? String.format(Formatting.GOLD + "[%.1f]", health) :
                                    String.format(Formatting.RED + "[%.1f]", health);

                    String name = e.getGameProfile().getName();
                    String displayStr = name + " " + healthStr + Formatting.GRAY + " [" + String.format("%.1f", range) + "]";

                    int color = e.isInvisible() ? 0x9B9B9B : 0xFFFFFF;
                    client.textRenderer.draw(matrices, displayStr, 10, y, color); // Переместите текст немного вправо
                    y += 12;
                }
            }
        }
    }




    @Override
    public void onTick() {
        // Проверяем, включен ли модуль, и вызываем onRenderGameOverlay, если включен
        if (this.isEnabled()) {
            onRenderGameOverlay(new MatrixStack(), 0);
        }
        super.onTick();
    }

    @Override
    public void onDisable() {
        // Логика для отключения, если необходимо
        super.onDisable();
    }
}
