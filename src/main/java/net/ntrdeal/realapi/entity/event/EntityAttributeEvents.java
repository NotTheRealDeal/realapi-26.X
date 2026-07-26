package net.ntrdeal.realapi.entity.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

public final class EntityAttributeEvents {
    private EntityAttributeEvents(){}

    public static final Event<Updated> UPDATED = EventFactory.createArrayBacked(Updated.class, events -> (entity, attribute) -> {
        for (Updated event : events) {
            event.update(entity, attribute);
        }
    });

    @FunctionalInterface
    public interface Updated {
        void update(LivingEntity entity, Holder<Attribute> attribute);
    }
}
