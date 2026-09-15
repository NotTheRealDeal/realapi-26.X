package net.ntrdeal.realapi.compat.cardinal;

import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentProvider;
import org.ladysnake.cca.api.v8.component.CardinalComponent;

public interface KeyedComponent<C extends KeyedComponent<C>> extends CardinalComponent {
    ComponentKey<C> key();
    ComponentProvider access();

    default void sync() {
        this.key().sync(this.access());
    }
}