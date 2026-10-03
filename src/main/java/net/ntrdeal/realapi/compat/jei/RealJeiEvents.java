package net.ntrdeal.realapi.compat.jei;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public final class RealJeiEvents {
    private RealJeiEvents(){}

    public static final Event<HideItemEvent> HIDE_OUTPUT = EventFactory.createWithPhases(HideItemEvent.class, events -> predicate -> {
        for (HideItemEvent event : events) predicate = event.getPredicate(predicate);
        return predicate;
    }, Event.DEFAULT_PHASE);

    public static final Event<HideItemEvent> HIDE_SIDEBAR = EventFactory.createWithPhases(HideItemEvent.class, events -> predicate -> {
        for (HideItemEvent event : events) predicate = event.getPredicate(predicate);
        return predicate;
    }, Event.DEFAULT_PHASE);

    @FunctionalInterface
    public interface HideItemEvent {
        Predicate<ItemStack> getPredicate(Predicate<ItemStack> predicate);
    }
}
