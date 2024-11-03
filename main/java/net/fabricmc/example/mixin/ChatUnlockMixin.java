package net.fabricmc.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.options.ChatVisibility;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class ChatUnlockMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        KeyBinding chatKey = client.options.keyChat;
        KeyBinding commandKey = client.options.keyCommand;

        if (chatKey.wasPressed() && !client.options.chatVisibility.equals(ChatVisibility.HIDDEN)) {
            client.openScreen(new ChatScreen(""));
        } else if (commandKey.wasPressed() && !client.options.chatVisibility.equals(ChatVisibility.HIDDEN)) {
            client.openScreen(new ChatScreen("/"));
        }
    }
}
