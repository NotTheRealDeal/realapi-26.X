package net.ntrdeal.realapi.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.ntrdeal.realapi.RealAPI;

public final class RealEffectTags {
    public static final TagKey<MobEffect> CANNOT_CLEAR = bind("cannot_clear");

    private static TagKey<MobEffect> bind(final String name) {
        return TagKey.create(Registries.MOB_EFFECT, RealAPI.id(name));
    }
}
