package net.ntrdeal.realapi.item.component.type;

import java.util.Collection;
import java.util.function.Consumer;

public interface ClassToTypeHolder {
    default <T> void runAllOfClass(Class<? extends T> clazz, Consumer<T> consumer) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    default <T> Collection<T> getAllOfClass(Class<? extends T> clazz) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }
}
