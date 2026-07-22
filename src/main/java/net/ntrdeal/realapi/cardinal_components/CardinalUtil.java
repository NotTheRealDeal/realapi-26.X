package net.ntrdeal.realapi.cardinal_components;

import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentProvider;
import org.ladysnake.cca.api.v8.component.CardinalComponent;

import java.util.function.Consumer;
import java.util.function.Function;

public class CardinalUtil {
    public static <C extends CardinalComponent> void ifPresent(ComponentKey<C> key, Object object, Consumer<C> consumer) {
        if (object instanceof ComponentProvider && key.getNullable(object) instanceof C component) consumer.accept(component);
    }

    public static <C extends CardinalComponent, R> R returnOr(ComponentKey<C> key, Object object, Function<C, R> function, R defaultValue) {
        return object instanceof ComponentProvider && key.getNullable(object) instanceof C component ? function.apply(component) : defaultValue;
    }
}
