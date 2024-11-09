package net.fabricmc.example.Util;

import net.minecraft.client.particle.Particle;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ParticleCreateCallback {
    void onParticleCreate(Particle particle);

    Event<ParticleCreateCallback> EVENT = EventFactory.createArrayBacked(ParticleCreateCallback.class,
            (listeners) -> (particle) -> {
                for (ParticleCreateCallback listener : listeners) {
                    listener.onParticleCreate(particle);
                }
            }
    );
}
