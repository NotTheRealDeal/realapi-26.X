package net.ntrdeal.realapi.recipe;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.RecipeHolder;

public final class RealRecipeEvents {
    private RealRecipeEvents(){}

    public static final Event<CanCraftEvent> CAN_CRAFT = EventFactory.createWithPhases(CanCraftEvent.class, events -> (player, holder, current) -> {
        for (CanCraftEvent event : events) current = event.canCraft(player, holder, current);
        return current;
    }, Event.DEFAULT_PHASE);

    @FunctionalInterface
    public interface CanCraftEvent {
        boolean canCraft(ServerPlayer player, RecipeHolder<?> holder, boolean current);
    }
}
