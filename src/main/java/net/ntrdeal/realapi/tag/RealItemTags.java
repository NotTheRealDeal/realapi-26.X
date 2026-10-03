package net.ntrdeal.realapi.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.compat.jei.RealJeiEvents;
import net.ntrdeal.realapi.util.registry.TagCreator;

public final class RealItemTags {
    private RealItemTags(){}

    private static final TagCreator<Item> CREATOR = TagCreator.of(Registries.ITEM, RealAPI::id);

    public static final TagKey<Item> KEEP_ON_DEATH = CREATOR.create("keep_on_death");
    public static final TagKey<Item> HIDE_RECIPE = CREATOR.create("hide_recipe");

    public static void register() {
        RealJeiEvents.HIDE_OUTPUT.register(predicate -> predicate.or(stack -> stack.is(HIDE_RECIPE)));
    }
}