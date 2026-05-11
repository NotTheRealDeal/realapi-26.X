package net.ntrdeal.realapi.client.item.stack_holder;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.ntrdeal.realapi.item.component.BundleLikeData;
import org.jspecify.annotations.Nullable;

public record BundleLikeHasIndexed() implements ConditionalItemModelProperty {
    public static final MapCodec<BundleLikeHasIndexed> MAP_CODEC = MapCodec.unit(new BundleLikeHasIndexed());

    @Override
    public boolean get(
            ItemStack stack,
            @Nullable ClientLevel level,
            @Nullable LivingEntity owner,
            int seed,
            ItemDisplayContext context
    ) {
        return BundleLikeData.getData(stack).map(data -> data.index(stack) != -1).orElse(false);
    }

    @Override
    public MapCodec<? extends ConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }
}
