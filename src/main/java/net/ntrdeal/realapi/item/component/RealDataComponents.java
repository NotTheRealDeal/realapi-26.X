package net.ntrdeal.realapi.item.component;

import net.minecraft.core.component.DataComponentType;
import net.ntrdeal.realapi.entity.event.KeepOnDeathEvent;
import net.ntrdeal.realapi.reference.RealDataComponentIds;
import net.ntrdeal.realapi.util.RegistryUtil;

public class RealDataComponents {
    public static final DataComponentType<KeepOnDeath> KEEP_ON_DEATH = RegistryUtil.ComponentUtil.register(RealDataComponentIds.KEEP_ON_DEATH, builder ->
            builder.persistent(KeepOnDeath.CODEC).networkSynchronized(KeepOnDeath.STREAM_CODEC)
    );

    public static final DataComponentType<BundleLikeData> BUNDLE_LIKE = RegistryUtil.ComponentUtil.register(RealDataComponentIds.BUNDLE_LIKE, builder ->
            builder.persistent(BundleLikeData.CODEC).networkSynchronized(BundleLikeData.STREAM_CODEC)
    );

    public static void register() {
        KeepOnDeathEvent.register();
    }
}
