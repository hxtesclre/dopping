package net.fabricmc.example.module.Movement;

import net.fabricmc.example.module.Mod;
import net.fabricmc.example.module.ModuleManager;
import net.fabricmc.example.module.Setting.BooleanSetting;
import net.fabricmc.example.module.Setting.NumberSetting;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class AutoWalk extends Mod {

    public NumberSetting speed = new NumberSetting("Speed", 0, 10, 1, 0.1);
    public BooleanSetting debugMode = new BooleanSetting("Debug Mode", false);
    private boolean isWalking = false; // Флаг для управления состоянием движения
    private final List<BlockPos> pathPoints = new ArrayList<>(); // Список точек маршрута
    private int currentPointIndex = 0; // Текущий индекс в списке точек
    private long lastAddTime = 0; // Время последнего добавления точки
    private final long addDelay = 500; // Задержка в миллисекундах между добавлениями точек
    private boolean keyPressed = false;
    private boolean debugModePrevState = false;

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public AutoWalk() {
        super("AutoWalk", "Automatically walks to the target position", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_I); // Назначаем клавишу I для активации/деактивации движения
        addSettings(speed, debugMode);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (mc.currentScreen == null) {
                handleKeyInput();
                handleAutoWalking();
            }
        });

        WorldRenderEvents.LAST.register(context -> {
            if (debugMode.isEnabled() || isWalking) {
                renderPath(context.matrixStack());
            }
        });
    }

    @Override
    public String getDisplayName() {
        if (debugMode.isEnabled()) {
            return "AutoWalk (debug)";
        } else if (isWalking) {
            return "AutoWalk (enabled)";
        } else {
            return "AutoWalk";
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            isWalking = false; // Отключаем автоходьбу, если модуль отключается
        }
    }


    private void handleKeyInput() {
        if (GLFW.glfwGetKey(mc.getWindow().getHandle(), this.getKey()) == GLFW.GLFW_PRESS) {
            if (!keyPressed) {
                keyPressed = true;
                if (debugMode.isEnabled()) {
                    if (System.currentTimeMillis() - lastAddTime > addDelay) {
                        setPathPoint(); // В режиме debug добавляем точку маршрута с задержкой
                        lastAddTime = System.currentTimeMillis(); // Обновляем время последнего добавления точки
                    }
                } else {
                    toggleWalking(); // В обычном режиме включаем/выключаем движение
                }
            }
        } else {
            keyPressed = false;
        }

        if (GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_C) == GLFW.GLFW_PRESS) {
            clearPath(); // Очищаем путь при нажатии клавиши C
        }

        // Проверка на изменение состояния debugMode
        if (debugModePrevState && !debugMode.isEnabled()) {
            if (isWalking) {
                isWalking = false; // Отключаем автоходьбу при выходе из режима дебага
            }
        }
        debugModePrevState = debugMode.isEnabled();
    }








    private void handleAutoWalking() {
        if (isWalking && currentPointIndex < pathPoints.size()) {
            walkToTarget(pathPoints.get(currentPointIndex)); // Ходим к текущей точке пути
        }
    }

    private void renderPath(MatrixStack matrices) {
        Vec3d cameraPos = mc.gameRenderer.getCamera().getPos();

        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.lineWidth(2.0f);

        GL11.glPushMatrix();
        GL11.glTranslated(-cameraPos.getX(), -cameraPos.getY(), -cameraPos.getZ());
        GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.5f);

        // Отрисовка линий маршрута
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i < pathPoints.size(); i++) {
            BlockPos point = pathPoints.get(i);
            GL11.glVertex3d(point.getX() + 0.5, point.getY() + 0.5, point.getZ() + 0.5);
        }
        GL11.glEnd();

        GL11.glPopMatrix();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();

        // Выделение блоков контрольных точек
        for (BlockPos point : pathPoints) {
            renderBox(matrices, point);
        }
    }

    private void renderBox(MatrixStack matrices, BlockPos pos) {
        Vec3d cameraPos = mc.gameRenderer.getCamera().getPos();
        double x = pos.getX() - cameraPos.getX();
        double y = pos.getY() - cameraPos.getY();
        double z = pos.getZ() - cameraPos.getZ();

        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        GL11.glLineWidth(2.0f);
        GL11.glBegin(GL11.GL_LINES);

        GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.5f); // Red color with 50% transparency

        // Draw lines for the box
        drawLine(x, y, z, x + 1, y, z);
        drawLine(x, y, z, x, y + 1, z);
        drawLine(x, y, z, x, y, z + 1);
        drawLine(x + 1, y, z, x + 1, y + 1, z);
        drawLine(x + 1, y, z, x + 1, y, z + 1);
        drawLine(x, y + 1, z, x + 1, y + 1, z);
        drawLine(x, y + 1, z, x, y + 1, z + 1);
        drawLine(x, y, z + 1, x + 1, y, z + 1);
        drawLine(x, y, z + 1, x, y + 1, z + 1);
        drawLine(x + 1, y + 1, z, x + 1, y + 1, z + 1);
        drawLine(x + 1, y, z + 1, x + 1, y + 1, z + 1);
        drawLine(x, y + 1, z + 1, x + 1, y + 1, z + 1);

        GL11.glEnd();

        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    private void drawLine(double x1, double y1, double z1, double x2, double y2, double z2) {
        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x2, y2, z2);
    }

    private void displayModuleStatus() {
        String status;
        if (debugMode.isEnabled()) {
            status = "AutoWalk (debug)";
        } else if (isWalking) {
            status = "AutoWalk (enabled)";
        } else {
            status = "AutoWalk";
        }
        mc.player.sendMessage(Text.of(status), true); // true для отображения на экране
    }




    private void walkToTarget(BlockPos targetPos) {
        if (!mc.player.isOnGround()) {
            return; // Если персонаж не на земле, не выполнять движение
        }

        Vec3d playerPos = mc.player.getPos();
        double distance = playerPos.distanceTo(Vec3d.ofBottomCenter(targetPos));

        if (distance < 1.0) {
            currentPointIndex++; // Переходим к следующей точке
            if (currentPointIndex >= pathPoints.size()) {
                isWalking = false; // Останавливаем движение, если достигли последней точки
                currentPointIndex = 0; // Сбрасываем индекс для повторного использования
            }
            return;
        }

        // Рассчитываем вектор направления и нормализуем его
        Vec3d direction = new Vec3d(targetPos.getX() - playerPos.x, 0, targetPos.getZ() - playerPos.z).normalize();
        mc.player.setVelocity(direction.multiply(speed.getValueFloat() * 0.05)); // Устанавливаем скорость движения с учетом настройки
    }

    private void toggleWalking() {
        isWalking = !isWalking; // Переключаем состояние движения при нажатии клавиши
        if (isWalking) {
            currentPointIndex = 0; // Сбрасываем индекс для начала движения с первой точки
        }
    }




    private void setPathPoint() {
        BlockPos playerPos = mc.player.getBlockPos();
        pathPoints.add(playerPos); // Добавляем текущую позицию игрока в список точек маршрута
        mc.player.sendMessage(Text.of("Added path point: " + playerPos), false); // Отправляем сообщение игроку (для визуализации)
        displayModuleStatus(); // Отображаем статус модуля
    }

    private void clearPath() {
        pathPoints.clear(); // Очищаем список точек маршрута
        mc.player.sendMessage(Text.of("Path cleared"), false); // Отправляем сообщение игроку (для визуализации)
        displayModuleStatus(); // Отображаем статус модуля после очистки пути
    }

}
