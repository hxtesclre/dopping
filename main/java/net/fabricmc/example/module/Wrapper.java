package net.fabricmc.example.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

public class Wrapper {
    public static final MinecraftClient INSTANCE = MinecraftClient.getInstance();

    public static PlayerEntity player() {
        return INSTANCE.player;
    }

    public static ClientWorld world() {
        return (ClientWorld) INSTANCE.world;
    }

    public static List<PlayerEntity> getPlayersList() {
        ClientWorld world = world();
        return world.getPlayers().stream()
                .map(player -> (PlayerEntity) player)
                .collect(Collectors.toList());
    }
}