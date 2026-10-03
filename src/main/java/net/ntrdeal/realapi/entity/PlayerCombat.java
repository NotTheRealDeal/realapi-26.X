package net.ntrdeal.realapi.entity;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.Player;
import net.ntrdeal.realapi.RealAPI;

public record PlayerCombat(int lastHit) {
    public static final StreamCodec<ByteBuf, PlayerCombat> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, PlayerCombat::lastHit,
            PlayerCombat::new
    );

    public static final AttachmentType<PlayerCombat> TYPE = AttachmentRegistry.create(
            RealAPI.id("player_combat"), builder -> builder
                    .initializer(() -> new PlayerCombat(0))
                    .syncWith(STREAM_CODEC, AttachmentSyncPredicate.all())
    );

    public boolean inCombat(Player player, int tickTime, boolean lenient) {
        return (lenient && this.lastHit == 0) || (player.tickCount - this.lastHit) <= tickTime;
    }

    public static void register() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register((
                entity, source, _,
                _, _
        ) -> {
            Entity sourceEntity = source.getEntity();
            if (entity.is(EntityTypes.PLAYER) && sourceEntity != null && sourceEntity.is(EntityTypes.PLAYER)) entity.setAttached(
                    TYPE, new PlayerCombat(entity.tickCount)
            );
        });
    }
}