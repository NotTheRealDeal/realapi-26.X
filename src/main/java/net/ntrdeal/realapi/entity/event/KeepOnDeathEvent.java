package net.ntrdeal.realapi.entity.event;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.Identifier;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.item.component.RealDataComponents;
import net.ntrdeal.realapi.mixin.attribute.AttributeMapAccessor;
import net.ntrdeal.realapi.mixin.attribute.PermanentModifiers;
import net.ntrdeal.realapi.tag.RealItemTags;

import java.util.List;

@FunctionalInterface
public interface KeepOnDeathEvent {
    String NAMESPACE = "keep_on_death";

    AttachmentType<List<ItemStackWithSlot>> TYPE = AttachmentRegistry.create(
            RealAPI.id(NAMESPACE), builder -> builder.persistent(ItemStackWithSlot.CODEC.listOf())
    );

    Event<KeepOnDeathEvent> EVENT = EventFactory.createArrayBacked(KeepOnDeathEvent.class, events -> (player, stack) -> {
        for (KeepOnDeathEvent event : events) {
            if (event.keepOnDeath(player, stack)) return true;
        }
        return false;
    });

    boolean keepOnDeath(Player player, ItemStack stack);

    static AttributeModifier keepOnDeath(AttributeModifier modifier) {
        return new AttributeModifier(
                Identifier.fromNamespaceAndPath(NAMESPACE, modifier.id().toString().replace(":", "_")),
                modifier.amount(), modifier.operation()
        );
    }

    static void register() {
        EVENT.register((_, stack) -> stack.is(RealItemTags.KEEP_ON_DEATH) || stack.has(RealDataComponents.KEEP_ON_DEATH));

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (alive) return;

            for (ItemStackWithSlot stackWithSlot : oldPlayer.getAttachedOrElse(TYPE, List.of())) {
                ItemStack stack = stackWithSlot.stack();
                newPlayer.getInventory().add(stackWithSlot.slot(), stack);
                if (!stack.isEmpty()) newPlayer.getInventory().add(stack);
            }

            ((AttributeMapAccessor)newPlayer.getAttributes()).getAttributes().values().forEach(newInstance -> {
                AttributeInstance oldInstance = oldPlayer.getAttribute(newInstance.getAttribute());
                if (oldInstance == null) return;

                oldInstance.getModifiers().forEach(modifier -> {
                    if (!modifier.id().getNamespace().equals(NAMESPACE)) return;
                    if (((PermanentModifiers)oldInstance).get().containsKey(modifier.id())) newInstance.addPermanentModifier(modifier);
                    else newInstance.addTransientModifier(modifier);
                });
            });
        });
    }
}
