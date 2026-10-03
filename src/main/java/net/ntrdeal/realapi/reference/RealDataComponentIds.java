package net.ntrdeal.realapi.reference;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.util.registry.ResourceCreator;

public final class RealDataComponentIds {
    private RealDataComponentIds(){}

    private static final ResourceCreator<DataComponentType<?>> CREATOR = ResourceCreator.of(Registries.DATA_COMPONENT_TYPE, RealAPI::id);

    public static final ResourceKey<DataComponentType<?>> KEEP_ON_DEATH = CREATOR.create("keep_on_death");
    public static final ResourceKey<DataComponentType<?>> BUNDLE_LIKE = CREATOR.create("bundle_like");
    public static final ResourceKey<DataComponentType<?>> PREVENT_CONSUMPTION = CREATOR.create("prevent_consumption");
}
