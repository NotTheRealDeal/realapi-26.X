package net.ntrdeal.realapi.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.util.RegistryUtil;

public final class RealAttributeTags {
    private static final RegistryUtil.TagCreator<Attribute> CREATOR = RegistryUtil.tagCreator(Registries.ATTRIBUTE, RealAPI::id);

    public static final TagKey<Attribute> DIMENSIONS_REFRESHER = CREATOR.create("dimensions_refresher");
}