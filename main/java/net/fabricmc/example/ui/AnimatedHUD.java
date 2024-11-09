package net.fabricmc.example.ui;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import org.lwjgl.glfw.GLFW;

public class AnimatedHUD {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private float scale = 0.1f;        // Текущий масштаб для анимации
    private float targetScale = 0.1f;  // Целевой масштаб для плавного перехода
    private float rotation = 0;        // Текущий угол поворота
    private float targetRotation = 0;  // Целевой угол поворота
    private boolean animationActive = false;  // Флаг для активации анимации
    private boolean isVisible = false; // Флаг для отслеживания видимости прямоугольника
    private float animationProgress = 0; // Прогресс анимации (от 0 до 1)
    private boolean keyPressed = false; // Флаг для отслеживания состояния нажатия клавиши

    public AnimatedHUD() {
        HudRenderCallback.EVENT.register(this::onRender);
    }

    private void onRender(MatrixStack matrixStack, float tickDelta) {
        // Обработка нажатий клавиш для активации/деактивации анимации
        if (GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_G) == GLFW.GLFW_PRESS) {
            if (!keyPressed) { // Если клавиша не была нажата ранее
                toggleAnimation(); // Запускаем или останавливаем анимацию
                keyPressed = true; // Устанавливаем флаг, что клавиша нажата
            }
        } else {
            keyPressed = false; // Сбрасываем флаг, если клавиша не нажата
        }

        // Закрытие интерфейса при нажатии ESC
        if (GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS && isVisible) {
            toggleAnimation(); // Запускаем анимацию сворачивания
        }

        // Плавное изменение масштаба и угла поворота
        if (animationActive) {
            if (targetScale > scale) {
                animationProgress += 0.02f; // Увеличиваем прогресс анимации появления
            } else {
                animationProgress += 0.04f; // Увеличиваем прогресс анимации исчезновения
            }

            if (animationProgress > 1.0f) {
                animationProgress = 1.0f;
                animationActive = false; // Завершаем анимацию
                if (scale == 0.1f) {
                    isVisible = false; // Если масштаб минимальный, скрываем прямоугольник
                }
            }

            // Плавное приближение к целевым значениям с замедлением в конце
            float t = animationProgress;
            t = (float) (0.5 * (1 - Math.cos(Math.PI * t))); // Используем метод замедления в конце
            scale = (1 - t) * scale + t * targetScale; // Интерполяция масштаба
            rotation = (1 - t) * rotation + t * targetRotation; // Интерполяция угла поворота
        }

        // Включаем курсор и отключаем движения, если прямоугольник видим
        if (isVisible) {
            GLFW.glfwSetInputMode(mc.getWindow().getHandle(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL); // Включаем курсор
            mc.mouse.unlockCursor(); // Отключаем управление мышью
            renderRectangle(matrixStack);
        } else {
            GLFW.glfwSetInputMode(mc.getWindow().getHandle(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED); // Отключаем курсор
            mc.mouse.lockCursor(); // Включаем управление мышью
        }
    }

    private void toggleAnimation() {
        if (!animationActive) {
            if (!isVisible) {
                // Начало анимации появления
                isVisible = true;
                targetScale = 3.0f; // Увеличиваем до 3.0f
                targetRotation = 360; // Полный оборот
                GLFW.glfwSetInputMode(mc.getWindow().getHandle(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL); // Включаем курсор
                mc.mouse.unlockCursor(); // Отключаем управление мышью
            } else {
                // Начало анимации исчезновения
                targetScale = 0.1f; // Уменьшаем до 0.1f
                targetRotation = 0; // Возвращение в исходное положение
                GLFW.glfwSetInputMode(mc.getWindow().getHandle(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED); // Отключаем курсор
                mc.mouse.lockCursor(); // Включаем управление мышью
            }
            animationProgress = 0; // Сброс прогресса анимации
            animationActive = true; // Запуск анимации
        }
    }

    private void renderRectangle(MatrixStack matrixStack) {
        int screenWidth = mc.getWindow().getScaledWidth();
        int screenHeight = mc.getWindow().getScaledHeight();
        int xCenter = screenWidth / 2;
        int yCenter = screenHeight / 2;

        matrixStack.push();
        matrixStack.translate(xCenter, yCenter, 0);
        matrixStack.scale(scale, scale, 1);
        matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(rotation));

        // Отрисовываем прямоугольник 80x60 пикселей (соотношение 4:3)
        fill(matrixStack, -40, -30, 40, 30, 0xFFFFFFFF);  // Белый цвет

        matrixStack.pop();
    }

    // Метод для отрисовки цветного прямоугольника
    private void fill(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {
        MinecraftClient.getInstance().inGameHud.fill(matrices, x1, y1, x2, y2, color);
    }
}
