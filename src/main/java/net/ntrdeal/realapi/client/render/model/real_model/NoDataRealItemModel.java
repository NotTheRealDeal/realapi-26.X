package net.ntrdeal.realapi.client.render.model.real_model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public interface NoDataRealItemModel extends RealItemModel<Void> {
    RenderStateDataKey<Void> DATA_KEY = RenderStateDataKey.create();
    @Override default @Nullable Void extractData(Update<Void> extracting) {return null;}
    @Override default RenderStateDataKey<Void> dataKey() {return DATA_KEY;}
}
