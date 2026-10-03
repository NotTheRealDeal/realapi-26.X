package net.ntrdeal.realapi.util.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.function.Function;

public record ResourceCreator<T>(ResourceKey<? extends Registry<T>> registry, Function<String, Identifier> function) {
    public ResourceKey<T> create(String path) {
        return ResourceKey.create(this.registry, this.function.apply(path));
    }

    public static <T> ResourceCreator<T> of(ResourceKey<? extends Registry<T>> registry, Function<String, Identifier> function) {
        return new ResourceCreator<>(registry, function);
    }
}
