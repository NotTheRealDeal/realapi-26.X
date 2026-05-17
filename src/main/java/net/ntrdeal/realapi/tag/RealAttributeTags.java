package net.ntrdeal.realapi.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.ntrdeal.realapi.RealAPI;

public final class RealAttributeTags {
    public static final TagKey<Attribute> DIMENSIONS_REFRESHER = bind("dimensions_refresher");

    private static TagKey<Attribute> bind(final String name) {
        return TagKey.create(Registries.ATTRIBUTE, RealAPI.id(name));
    }
}