package net.fabricmc.example.module.Render;

import net.fabricmc.example.module.Mod;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.example.Util.ParticleCreateCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ParticleTracker extends Mod {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final List<Particle> trackedParticles = new ArrayList<>();
    private final List<Text> particleMessages = new ArrayList<>();
    private KeyBinding keyBinding;

    public ParticleTracker() {
        super("ParticleTracker", "Tracks particles in the world", Category.RENDER);

        // Инициализация клавиши
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.fabricmc.example.particletracker",
                GLFW.GLFW_KEY_P,
                "category.fabricmc.example.render"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) {
                this.toggle(); // Переключаем состояние модуля при нажатии клавиши
            }

            trackedParticles.removeIf(Particle::isAlive); // Удаляем мертвые частицы

            // Удаляем устаревшие сообщения каждые 5 секунд
            if (!particleMessages.isEmpty() && System.currentTimeMillis() % (60 * 5) == 0) {
                particleMessages.clear();
            }
        });


        ParticleCreateCallback.EVENT.register(particle -> {
            trackedParticles.add(particle);
            Text message = Text.of("Появилась частица: " + particle.getType().toString());
            particleMessages.add(message);
            System.out.println("Particle created: " + particle.getType().toString()); // Добавляем сообщение в консоль для отладки
        });


        // Регистрируем событие рендеринга для отображения сообщений
        WorldRenderEvents.END.register(context -> {
            onRender(context.matrixStack());
        });
    }

    private void onRender(MatrixStack matrices) {
        if (!particleMessages.isEmpty()) {
            System.out.println("Rendering particle messages"); // Отладка
            TextRenderer textRenderer = mc.textRenderer;
            int y = 10;
            for (Text message : particleMessages) {
                System.out.println("Rendering message: " + message.getString()); // Отладка текста
                textRenderer.drawWithShadow(matrices, message, 10, y, 0xFFFFFF);
                y += 10; // Расстояние между сообщениями
            }
        }
    }



}
