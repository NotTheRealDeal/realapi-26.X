package net.ntrdeal.realapi.util;

import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.TypedDataComponent;

import java.util.stream.Stream;

public class ItemStackUtil {
    @SuppressWarnings("unchecked")
    public static <T> Stream<TypedDataComponent<T>> getAllOfType(DataComponentHolder holder, Class<? extends T> clazz) {
        return holder.getComponents().stream().mapMulti((component, consumer) -> {
            Object value = component.value();
            if (clazz.isInstance(value)) consumer.accept((TypedDataComponent<T>) component);
        });
    }
}
