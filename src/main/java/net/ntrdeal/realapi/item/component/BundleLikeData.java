package net.ntrdeal.realapi.item.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.ntrdeal.realapi.item.stack_holder.HolderBuilder;
import net.ntrdeal.realapi.item.stack_holder.StackHolder;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("unchecked")
public record BundleLikeData(DataComponentType<StackHolder<?>> type) {
    public static final Codec<BundleLikeData> CODEC = DataComponentType.CODEC.xmap(
            type -> new BundleLikeData((DataComponentType<StackHolder<?>>)type),
            BundleLikeData::type
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, BundleLikeData> STREAM_CODEC = DataComponentType.STREAM_CODEC.map(
            type -> new BundleLikeData((DataComponentType<StackHolder<?>>)type),
            BundleLikeData::type
    );

    public <T extends StackHolder<T>> void setIndex(ItemStack stack, int index) {
        T holder = (T) stack.get(this.type);
        if (holder == null) return;

        HolderBuilder<T> builder = holder.builder();
        builder.setIndex(index);
        stack.set(this.type, builder.build());
    }

    public int index(ItemStack stack) {
        StackHolder<?> holder = stack.get(this.type);
        if (holder == null) return -1;
        else return holder.index();
    }

    public int displayCount(ItemStack stack) {
        StackHolder<?> holder = stack.get(this.type);
        if (holder == null) return 0;
        else return holder.getNumberOfItemsToShow();
    }

    @Nullable
    public ItemStackTemplate selected(ItemStack stack) {
        StackHolder<?> holder = stack.get(this.type);
        if (holder == null) return null;
        else return holder.getSelectedItem();
    }

    public static Optional<BundleLikeData> getData(ItemStack stack) {
        return Optional.ofNullable(stack.get(RealDataComponents.BUNDLE_LIKE));
    }
}
