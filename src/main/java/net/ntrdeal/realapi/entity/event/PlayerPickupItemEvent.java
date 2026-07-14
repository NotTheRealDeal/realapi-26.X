package net.ntrdeal.realapi.entity.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public interface PlayerPickupItemEvent {
    Event<ReplaceItemEvent> REPLACE_ITEM = EventFactory.createWithPhases(ReplaceItemEvent.class, events -> (player, entity, stack) -> {
        for (ReplaceItemEvent event : events) {
            stack = event.replaceItem(player, entity, stack);
        }
        return stack;
    }, Event.DEFAULT_PHASE);

    Event<CanPickupEvent> CAN_PICKUP = EventFactory.createWithPhases(CanPickupEvent.class, events -> (player, entity, stack, canPickup) -> {
        for (CanPickupEvent event : events) {
            canPickup = event.canPickup(player, entity, stack, canPickup);
        }
        return canPickup;
    }, Event.DEFAULT_PHASE);

    interface ReplaceItemEvent {
        @NonNull ItemStack replaceItem(Player player, ItemEntity entity, ItemStack stack);
    }

    interface CanPickupEvent {
        boolean canPickup(Player player, ItemEntity entity, ItemStack stack, boolean canPickup);
    }
}