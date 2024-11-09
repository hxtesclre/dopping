package net.fabricmc.example.mixin;

import net.fabricmc.example.Util.ParticleCreateCallback;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDD)Lnet/minecraft/client/particle/Particle;", at = @At("RETURN"), cancellable = true)
    private void onAddParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> cir) {
        Particle particle = cir.getReturnValue();
        System.out.println("Particle added: " + particle.getType().toString());
        ParticleCreateCallback.EVENT.invoker().onParticleCreate(particle);
    }
}
