package net.ntrdeal.realapi.data;

import java.util.function.Supplier;

public class DataKey<T> {
    private final Supplier<String> name;

    private DataKey(Supplier<String> name) {
        this.name = name;
    }

    public static <T> DataKey<T> create(Supplier<String> name) {
        return new DataKey<>(name);
    }

    public static <T> DataKey<T> create() {
        return new DataKey<>(() -> "unnamed");
    }

    @Override
    public String toString() {
        return "DataKey(" + this.name.get() + ")";
    }
}
