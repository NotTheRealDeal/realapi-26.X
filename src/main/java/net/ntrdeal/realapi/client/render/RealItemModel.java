package net.ntrdeal.realapi.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RealItemModel<T> extends ItemModel {
    RenderStateDataKey<Map<Integer, List<RealItemModel<?>>>> RENDERER_KEY = RenderStateDataKey.create();
    int PRE_TRANSFORM = 0;
    int POST_TRANSFORM = 1;

    @Nullable T extractData(Update<T> extracting);
    void update(Update<T> extracted);
    void preRender(PreRender<T> preRender);
    RenderStateDataKey<T> dataKey();
    default int phase() {return PRE_TRANSFORM;}

    default void preRender(SubmitNodeCollector collector, ItemStackRenderState state, ItemStackRenderState.LayerRenderState layer, PoseStack poseStack, int light, int overlay, int outline) {
        this.preRender(new PreRender<>(collector, state, layer, poseStack, light, overlay, outline, state.getData(this.dataKey())));
    }

    @Override
    default void update(
            ItemStackRenderState state, ItemStack stack,
            ItemModelResolver resolver, ItemDisplayContext displayContext,
            @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed
    ) {
        Map<Integer, List<RealItemModel<?>>> models = state.getDataOrDefault(RENDERER_KEY, new HashMap<>());
        if (state.getData(RENDERER_KEY) == null) state.setData(RENDERER_KEY, models);
        models.computeIfAbsent(this.phase(), _ -> new ArrayList<>()).add(this);

        this.update(new Update<>(
                resolver, state, stack, displayContext,
                this.setData(resolver, state, stack, displayContext, level, owner, seed),
                level, owner, seed
        ));
    }

    default T setData(
            ItemModelResolver resolver, ItemStackRenderState state, ItemStack stack,
            ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed
    ) {
        T data = this.extractData(new Update<>(resolver, state, stack, displayContext, null, level, owner, seed));
        state.setData(this.dataKey(), data);
        return data;
    }
}