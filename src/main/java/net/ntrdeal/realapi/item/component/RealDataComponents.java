package net.ntrdeal.realapi.item.component;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.util.Unit;
import net.ntrdeal.realapi.entity.event.KeepOnDeathEvent;
import net.ntrdeal.realapi.item.component.custom.BundleLikeData;
import net.ntrdeal.realapi.item.component.custom.KeepOnDeath;
import net.ntrdeal.realapi.item.component.type.EquipmentChangeListener;
import net.ntrdeal.realapi.reference.RealDataComponentIds;
import net.ntrdeal.realapi.util.RegistryUtil;

public final class RealDataComponents {
    private RealDataComponents(){}

    public static final DataComponentType<KeepOnDeath> KEEP_ON_DEATH = RegistryUtil.ComponentUtil.register(RealDataComponentIds.KEEP_ON_DEATH, builder ->
            builder.persistent(KeepOnDeath.CODEC).networkSynchronized(KeepOnDeath.STREAM_CODEC)
    );

    public static final DataComponentType<BundleLikeData> BUNDLE_LIKE = RegistryUtil.ComponentUtil.register(RealDataComponentIds.BUNDLE_LIKE, builder ->
            builder.persistent(BundleLikeData.CODEC).networkSynchronized(BundleLikeData.STREAM_CODEC)
    );

    public static final DataComponentType<Unit> PREVENT_CONSUMPTION = RegistryUtil.ComponentUtil.register(RealDataComponentIds.PREVENT_CONSUMPTION, builder ->
            builder.persistent(Unit.CODEC).networkSynchronized(Unit.STREAM_CODEC)
    );

    public static void register() {
        KeepOnDeathEvent.register();

        ServerEntityEvents.EQUIPMENT_CHANGE.register((entity, slot, unequipped, equipped) -> {
            unequipped.runAllOfClass(EquipmentChangeListener.class, listener -> listener.unequipped(
                    unequipped, entity, slot, equipped
            ));
            equipped.runAllOfClass(EquipmentChangeListener.class, listener -> listener.equipped(
                    equipped, entity, slot, unequipped
            ));
        });
    }
}
