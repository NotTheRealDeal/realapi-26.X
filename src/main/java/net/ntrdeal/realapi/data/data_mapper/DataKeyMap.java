package net.ntrdeal.realapi.data.data_mapper;

import java.util.Map;

@SuppressWarnings("unchecked")
public interface DataKeyMap {
    Map<DataKey<?>, Object> dataMap();


    default void putData(DataKey<?> key, Object value) {
        this.dataMap().put(key, value);
    }

    default <T> T getData(DataKey<T> key) {
        return (T) this.dataMap().get(key);
    }

    default <T> T getDataOrDefault(DataKey<T> key, T defaultValue) {
        return (T) this.dataMap().getOrDefault(key, defaultValue);
    }
}
