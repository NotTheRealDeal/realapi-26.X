package net.ntrdeal.realapi.reference;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.ntrdeal.realapi.RealAPI;

public final class RealDataComponentIds {
    public static final ResourceKey<DataComponentType<?>> KEEP_ON_DEATH = create("keep_on_death");
    public static final ResourceKey<DataComponentType<?>> BUNDLE_LIKE = create("bundle_like");

    private static ResourceKey<DataComponentType<?>> create(String name) {
        return ResourceKey.create(Registries.DATA_COMPONENT_TYPE, RealAPI.id(name));
    }
}
