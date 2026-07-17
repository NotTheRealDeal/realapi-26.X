package net.ntrdeal.realapi.reference;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.util.RegistryUtil;

public final class RealDataComponentIds {
    private static final RegistryUtil.ResourceCreator<DataComponentType<?>> CREATOR = RegistryUtil.resourceCreator(Registries.DATA_COMPONENT_TYPE, RealAPI::id);

    public static final ResourceKey<DataComponentType<?>> KEEP_ON_DEATH = CREATOR.create("keep_on_death");
    public static final ResourceKey<DataComponentType<?>> BUNDLE_LIKE = CREATOR.create("bundle_like");
}
