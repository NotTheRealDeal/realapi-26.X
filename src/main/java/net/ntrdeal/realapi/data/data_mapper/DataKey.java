package net.ntrdeal.realapi.data.data_mapper;

public interface DataKey<T> {
    default T get(DataKeyMap map) {
        return map.getData(this);
    }

    default T getOrDefault(DataKeyMap map, T defaultValue) {
        return map.getDataOrDefault(this, defaultValue);
    }

    static <T> DataKey<T> create() {
        return new DataKey<>(){};
    }
}
