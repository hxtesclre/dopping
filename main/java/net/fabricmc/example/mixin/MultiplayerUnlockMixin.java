package net.fabricmc.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Mixin для разблокировки кнопки мультиплеера в главном меню.
 *
 * @reason Разблокировка кнопки мультиплеера.
 * @author ВашеИмя
 */
@Mixin(TitleScreen.class)
public abstract class MultiplayerUnlockMixin extends Screen {

    protected MultiplayerUnlockMixin(TranslatableText title) {
        super(title);
    }

    /**
     * Заменяет метод initWidgetsNormal для разблокировки кнопки мультиплеера.
     *
     * @param y        Координата Y для расположения кнопки
     * @param spacingY Интервал между кнопками
     * @reason Разблокировка кнопки мультиплеера.
     * @author ВашеИмя
     */
    @Overwrite
    private void initWidgetsNormal(int y, int spacingY) {
        this.addButton(new ButtonWidget(this.width / 2 - 100, y, 200, 20, new TranslatableText("menu.singleplayer"), (button) -> {
            MinecraftClient.getInstance().openScreen(new SelectWorldScreen(this));
        }));
        boolean bl = true; // Устанавливаем значение переменной для мультиплеера на true
        ButtonWidget.TooltipSupplier tooltipSupplier = bl ? ButtonWidget.EMPTY : (button, matrixStack, i, j) -> {
            if (!button.active) {
                this.renderOrderedTooltip(matrixStack, this.client.textRenderer.wrapLines(new TranslatableText("title.multiplayer.disabled"), Math.max(this.width / 2 - 43, 170)), i, j);
            }
        };
        ((ButtonWidget)this.addButton(new ButtonWidget(this.width / 2 - 100, y + spacingY * 1, 200, 20, new TranslatableText("menu.multiplayer"), (button) -> {
            Screen screen = this.client.options.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this);
            MinecraftClient.getInstance().openScreen(screen);
        }, tooltipSupplier))).active = bl;
        ((ButtonWidget)this.addButton(new ButtonWidget(this.width / 2 - 100, y + spacingY * 2, 200, 20, new TranslatableText("menu.online"), (button) -> {
            // Оставляем пустым или добавляем соответствующую логику
        }, tooltipSupplier))).active = bl;
    }
}
