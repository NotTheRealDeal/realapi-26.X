package net.ntrdeal.realapi.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.util.RegistryUtil;

public final class RealDamageTypeTags {
    private RealDamageTypeTags(){}

    private static final RegistryUtil.TagCreator<DamageType> CREATOR = RegistryUtil.tagCreator(Registries.DAMAGE_TYPE, RealAPI::id);

    public static final TagKey<DamageType> RANGED_ATTACK_MULTIPLIED = CREATOR.create("range_attack_multiplied");
}
