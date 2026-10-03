package net.ntrdeal.realapi.util.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;

import java.util.function.UnaryOperator;

public final class DataComponentRegistry {
    private DataComponentRegistry(){}

    public static <T> DataComponentType<T> register(ResourceKey<DataComponentType<?>> key, UnaryOperator<DataComponentType.Builder<T>> operator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, key, operator.apply(DataComponentType.builder()).build());
    }
}