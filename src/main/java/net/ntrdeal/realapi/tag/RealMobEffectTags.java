package net.ntrdeal.realapi.tag;

import net.fabricmc.fabric.api.entity.event.v1.effect.ServerMobEffectEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.util.RegistryUtil;

public final class RealMobEffectTags {
    private static final RegistryUtil.TagCreator<MobEffect> CREATOR = RegistryUtil.tagCreator(Registries.MOB_EFFECT, RealAPI::id);

    public static final TagKey<MobEffect> CANNOT_CLEAR = CREATOR.create("cannot_clear");
    public static final TagKey<MobEffect> PLAYER_ONLY = CREATOR.create("player_only");

    public static void register() {
        ServerMobEffectEvents.ALLOW_EARLY_REMOVE.register(
                (instance, _, ctx) -> ctx.isFromCommand() || !instance.getEffect().is(CANNOT_CLEAR)
        );

        ServerMobEffectEvents.ALLOW_ADD.register(
                (instance, entity, _) -> !instance.getEffect().is(PLAYER_ONLY) || entity instanceof Player
        );
    }
}
