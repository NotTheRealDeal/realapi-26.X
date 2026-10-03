package net.ntrdeal.realapi.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.util.registry.TagCreator;

public final class RealAttributeTags {
    private RealAttributeTags(){}

    private static final TagCreator<Attribute> CREATOR = TagCreator.of(Registries.ATTRIBUTE, RealAPI::id);

    public static final TagKey<Attribute> DIMENSIONS_REFRESHER = CREATOR.create("dimensions_refresher");
}