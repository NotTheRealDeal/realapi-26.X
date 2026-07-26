package net.ntrdeal.realapi.item.component.type;

import java.util.function.Consumer;

public interface ClassToTypeHolder {
    default <T> void runAllOfClass(Class<? extends T> clazz, Consumer<T> consumer) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }
}
