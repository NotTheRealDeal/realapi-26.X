package net.ntrdeal.realapi.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.util.RegistryUtil;

public final class RealEffectTags {
    private static final RegistryUtil.TagCreator<MobEffect> CREATOR = RegistryUtil.tagCreator(Registries.MOB_EFFECT, RealAPI::id);

    public static final TagKey<MobEffect> CANNOT_CLEAR = CREATOR.create("cannot_clear");
}
