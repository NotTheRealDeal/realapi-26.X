package net.ntrdeal.realapi.client.item.stack_holder;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.ntrdeal.realapi.item.component.BundleLikeData;
import org.joml.Matrix4fc;
import org.jspecify.annotations.Nullable;

public class BundleLikeSpecialRenderer implements ItemModel {
    public static final ItemModel INSTANCE = new BundleLikeSpecialRenderer();

    @Override
    public void update(
            ItemStackRenderState state,
            ItemStack stack,
            ItemModelResolver resolver,
            ItemDisplayContext context,
            @Nullable ClientLevel level,
            @Nullable ItemOwner owner,
            int seed
    ) {
        state.appendModelIdentityElement(this);

        BundleLikeData.getData(stack).ifPresent(data -> {
            if (data.selected(stack) instanceof ItemStackTemplate template) resolver.appendItemLayers(state, template.create(), context, level, owner, seed);
        });
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked() implements ItemModel.Unbaked {
        public static final MapCodec<BundleLikeSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new BundleLikeSpecialRenderer.Unbaked());

        @Override
        public MapCodec<BundleLikeSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public ItemModel bake(final ItemModel.BakingContext context, final Matrix4fc transformation) {
            return BundleLikeSpecialRenderer.INSTANCE;
        }

        @Override
        public void resolveDependencies(final ResolvableModel.Resolver resolver) {
        }
    }
}