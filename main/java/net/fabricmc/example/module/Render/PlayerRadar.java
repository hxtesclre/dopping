package net.fabricmc.example.module.Render;

import net.fabricmc.example.module.Mod;
import net.fabricmc.example.module.Wrapper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerRadar extends Mod implements HudRenderCallback {
    private Set<UUID> realPlayers = new HashSet<>();

    public PlayerRadar() {
        super("PlayerRadar", "Allows you to see near players", Category.RENDER);
        this.setKey(GLFW.GLFW_KEY_P);
        HudRenderCallback.EVENT.register(this);
    }

    @Override
    public void onHudRender(MatrixStack matrices, float tickDelta) {
        if (!this.isEnabled()) {
            return;
        }

        MinecraftClient client = Wrapper.INSTANCE;
        if (client.world != null && client.player != null) {
            int y = 10;
            for (Entity e : client.world.getPlayers()) {
                if (e instanceof PlayerEntity && e != client.player && realPlayers.contains(e.getUuid())) {
                    PlayerEntity player = (PlayerEntity) e;
                    float range = client.player.distanceTo(e);
                    float health = player.getHealth();

                    String healthStr = health >= 12.0 ? String.format("§2[%.1f]", health) :
                            health >= 4.0 ? String.format("§6[%.1f]", health) :
                                    String.format("§4[%.1f]", health);

                    String name = player.getGameProfile().getName();
                    if (name.contains("§") || name.length() > 16) {
                        continue;
                    }
                    String displayStr = name + " " + healthStr + " §7[" + String.format("%.1f", range) + "]";

                    int color = player.isInvisible() ? 0x9B9B9B : 0xFFFFFF;
                    client.textRenderer.draw(matrices, displayStr, 10, y, color);
                    y += 12;
                }
            }
        }
    }

    @Override
    public void onTick() {
        if (this.isEnabled()) {
            MinecraftClient client = Wrapper.INSTANCE;
            if (client.world != null && client.player != null) {
                realPlayers = client.getNetworkHandler().getPlayerList().stream()
                        .map(PlayerListEntry::getProfile)
                        .map(profile -> profile.getId())
                        .collect(Collectors.toSet());
            }
        }
        super.onTick();
    }

    @Override
    public void onDisable() {
        realPlayers.clear();
        super.onDisable();
    }
}
