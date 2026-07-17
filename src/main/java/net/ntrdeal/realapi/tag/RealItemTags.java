package net.ntrdeal.realapi.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.util.RegistryUtil;

public final class RealItemTags {
    private static final RegistryUtil.TagCreator<Item> CREATOR = RegistryUtil.tagCreator(Registries.ITEM, RealAPI::id);

    public static final TagKey<Item> KEEP_ON_DEATH = CREATOR.create("keep_on_death");
}