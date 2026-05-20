package net.ntrdeal.realapi.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import org.jspecify.annotations.Nullable;

public interface NoDataRealItemModel extends RealItemModel<Void> {
    RenderStateDataKey<Void> DATA_KEY = RenderStateDataKey.create();
    @Override default @Nullable Void extractData(Update<Void> extracting) {return null;}
    @Override default RenderStateDataKey<Void> dataKey() {return DATA_KEY;}
}
