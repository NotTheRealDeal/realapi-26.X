package net.ntrdeal.realapi.entity;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.ntrdeal.realapi.data.data_mapper.DataKey;
import net.ntrdeal.realapi.data.data_mapper.DataKeyMap;

import java.util.HashSet;
import java.util.Set;

public interface DamageSourcesKey<T> extends DataKey<T> {
    Set<DamageSourcesKey<?>> KEYS = new HashSet<>();

    T create(RegistryAccess access, DamageSources sources);

    default T getSources(Entity entity) {
        return this.get((DataKeyMap) entity.damageSources());
    }

    static <T> DamageSourcesKey<T> register(DamageSourcesKey<T> key) {
        KEYS.add(key);
        return key;
    }
}
