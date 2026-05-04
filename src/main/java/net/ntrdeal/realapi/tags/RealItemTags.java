package net.ntrdeal.realapi.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.ntrdeal.realapi.RealAPI;

public final class RealItemTags {
    public static final TagKey<Item> KEEP_ON_DEATH = bind("keep_on_death");

    private static TagKey<Item> bind(final String name) {
        return TagKey.create(Registries.ITEM, RealAPI.id(name));
    }
}