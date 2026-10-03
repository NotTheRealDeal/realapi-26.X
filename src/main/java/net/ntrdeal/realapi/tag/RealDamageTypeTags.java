package net.ntrdeal.realapi.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.util.registry.TagCreator;

public final class RealDamageTypeTags {
    private RealDamageTypeTags(){}

    private static final TagCreator<DamageType> CREATOR = TagCreator.of(Registries.DAMAGE_TYPE, RealAPI::id);

    public static final TagKey<DamageType> RANGED_ATTACK_MULTIPLIED = CREATOR.create("range_attack_multiplied");
}
