package net.ntrdeal.realapi.item.component.type;

import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;

import java.util.Set;

public interface RealComponentMap extends DataComponentMap {
    default <T> Set<DataComponentType<? extends T>> requestTypes(Class<? extends T> clazz) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    default void setCache(
            Reference2ObjectMap<Class<?>, Set<DataComponentType<?>>> classToType,
            Reference2ObjectMap<DataComponentType<?>, Set<Class<?>>> typeToClass
    ) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }
}
