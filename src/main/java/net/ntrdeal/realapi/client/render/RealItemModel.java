package net.ntrdeal.realapi.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface RealItemModel<T> extends ItemModel {
    RenderStateDataKey<List<RealItemModel<?>>> RENDERER_KEY = RenderStateDataKey.create();

    @Nullable T extractData(Update<T> extracting);
    void update(Update<T> extracted);
    void preRender(PreRender<T> preRender);
    RenderStateDataKey<T> dataKey();

    default void useData(
            SubmitNodeCollector collector, ItemStackRenderState state, PoseStack poseStack, ItemDisplayContext displayContext,
            ItemStackRenderState.FoilType foilType, List<BakedQuad> quads, int[] tints, int light, int overlay, int outline
    ) {
        this.preRender(new PreRender<>(
                collector, state, poseStack,
                displayContext, foilType, quads,
                tints, light, overlay, outline,
                state.getData(this.dataKey())
        ));
    }

    @Override
    default void update(
            ItemStackRenderState state, ItemStack stack,
            ItemModelResolver resolver, ItemDisplayContext displayContext,
            @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed
    ) {
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
        List<RealItemModel<?>> models = state.getDataOrDefault(RENDERER_KEY, new ArrayList<>());
        models.add(this);
        state.setData(RENDERER_KEY, models);

        T data = this.extractData(new Update<>(resolver, state, stack, displayContext, null, level, owner, seed));
        state.setData(this.dataKey(), data);
        return data;
    }
}