package net.fabricmc.example.mixin;

import net.fabricmc.example.ui.HUD;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("TAIL"), cancellable = true)
    public void renderHud(MatrixStack matricies, float tickDelta, CallbackInfo ci){
        HUD.render(matricies, tickDelta);
    }
}
