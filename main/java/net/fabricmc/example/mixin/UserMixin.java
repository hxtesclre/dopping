package net.fabricmc.example.mixin;

import net.minecraft.client.util.Session;
import net.fabricmc.example.ui.UserName;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Session.class)
public class UserMixin {

    @Shadow
    private String username;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(String username, String uuid, String accessToken, String accountType, CallbackInfo ci) {
        // Устанавливаем статическое имя пользователя
        this.username = UserName.getUserName();
    }
}
