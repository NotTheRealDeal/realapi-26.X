package net.ntrdeal.realapi.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public final class RealNetworking {
    public static void register() {
        PayloadTypeRegistry.serverboundPlay().register(BundleLikeIndexPacket.TYPE, BundleLikeIndexPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(BundleLikeIndexPacket.TYPE, BundleLikeIndexPacket::handle);
    }
}
