package net.ntrdeal.realapi.item.component;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.entity.event.KeepOnDeathEvent;

import java.util.function.UnaryOperator;

public class RealDataComponents {
    public static final DataComponentType<KeepOnDeath> KEEP_ON_DEATH = register("keep_on_death", builder ->
            builder.persistent(KeepOnDeath.CODEC).networkSynchronized(KeepOnDeath.STREAM_CODEC)
    );

    public static final DataComponentType<BundleLikeData> BUNDLE_LIKE = register("bundle_like", builder ->
            builder.persistent(BundleLikeData.CODEC).networkSynchronized(BundleLikeData.STREAM_CODEC)
    );

    public static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, RealAPI.id(name),
                builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register() {
        KeepOnDeathEvent.register();
    }
}
