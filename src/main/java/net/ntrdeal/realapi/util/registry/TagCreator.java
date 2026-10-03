package net.ntrdeal.realapi.util.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import java.util.function.Function;

public record TagCreator<T>(ResourceKey<? extends Registry<T>> registry, Function<String, Identifier> function) {
    public TagKey<T> create(String path) {
        return TagKey.create(this.registry, this.function.apply(path));
    }

    public static <T> TagCreator<T> of(ResourceKey<? extends Registry<T>> registry, Function<String, Identifier> function) {
        return new TagCreator<>(registry, function);
    }
}
