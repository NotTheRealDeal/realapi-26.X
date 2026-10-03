package net.ntrdeal.realapi.entity.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public final class RealLivingEntityEvents {
    private RealLivingEntityEvents(){}

    public static final Event<InvulnerableToEvent> INVULNERABLE_TO = EventFactory.createArrayBacked(InvulnerableToEvent.class, events -> (entity, attribute) -> {
        for (InvulnerableToEvent event : events) {
            if (event.isInvulnerableTo(entity, attribute)) return true;
        }
        return false;
    });

    @FunctionalInterface
    public interface InvulnerableToEvent {
        boolean isInvulnerableTo(LivingEntity entity, DamageSource source);
    }
}
