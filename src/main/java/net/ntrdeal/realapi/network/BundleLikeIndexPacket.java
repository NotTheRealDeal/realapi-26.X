package net.ntrdeal.realapi.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.item.component.BundleLikeData;

public record BundleLikeIndexPacket(int slotIndex, int index) implements CustomPacketPayload {
    public static final Type<BundleLikeIndexPacket> TYPE = new Type<>(RealAPI.id("bundle_like_index"));

    public static final StreamCodec<FriendlyByteBuf, BundleLikeIndexPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, BundleLikeIndexPacket::slotIndex,
            ByteBufCodecs.VAR_INT, BundleLikeIndexPacket::index,
            BundleLikeIndexPacket::new
    );

    @Override
    public Type<BundleLikeIndexPacket> type() {
        return TYPE;
    }

    public static void handle(BundleLikeIndexPacket packet, ServerPlayNetworking.Context context) {
        int slotIndex = packet.slotIndex(), index = packet.index();
        AbstractContainerMenu menu = context.player().containerMenu;

        if (slotIndex >= 0 && slotIndex < menu.slots.size()) {
            ItemStack stack = menu.slots.get(slotIndex).getItem();
            BundleLikeData.getData(stack).ifPresent(data -> data.setIndex(stack, index));
        }
    }
}
