package net.fabricmc.example.ui;

import net.fabricmc.example.module.Mod;
import net.fabricmc.example.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class HUD {
    private static MinecraftClient mc = MinecraftClient.getInstance();
    public static void render(MatrixStack matricies, float tickDelta){
        renderArrayList(matricies);
    }


    public static void renderArrayList(MatrixStack matricies){
        int index = 0;
        int sWidth = mc.getWindow().getScaledWidth();
        int sHeight = mc.getWindow().getScaledHeight();

        List<Mod> enabled = ModuleManager.INSTANCE.getEnableModules();

        enabled.sort(Comparator.comparingInt(m -> (int)mc.textRenderer.getWidth(((Mod)m).getDisplayName())).reversed());

        for(Mod module : enabled){
            mc.textRenderer.drawWithShadow(matricies, module.getDisplayName(), (sWidth - 4) - mc.textRenderer.getWidth(module.getDisplayName()), 10+(index * mc.textRenderer.fontHeight), -1);
            index++;
        }
    }
}
