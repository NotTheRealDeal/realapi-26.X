package net.ntrdeal.realapi.reference;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.util.RegistryUtil;

public final class RealAttributeIds {
    private static final RegistryUtil.ResourceCreator<Attribute> CREATOR = RegistryUtil.resourceCreator(Registries.ATTRIBUTE, RealAPI::id);

    public static final ResourceKey<Attribute> MOVEMENT_SCALE = CREATOR.create("movement_scale");
    public static final ResourceKey<Attribute> SHIELD_FRAGILITY = CREATOR.create("shield_fragility");
    public static final ResourceKey<Attribute> ARMOR_PENETRATION = CREATOR.create("armor_penetration");
    public static final ResourceKey<Attribute> APPETITE = CREATOR.create("appetite");
    public static final ResourceKey<Attribute> CHARGE_TIME = CREATOR.create("charge_time");
    public static final ResourceKey<Attribute> RANGED_ATTACK_MULTIPLIER = CREATOR.create("ranged_attack_multiplier");
    public static final ResourceKey<Attribute> BANE_OF_ADOLESCENCE = CREATOR.create("bane_of_adolescence");
    public static final ResourceKey<Attribute> FIRE_DAMAGE_MULTIPLIER = CREATOR.create("fire_damage_multiplier");
    public static final ResourceKey<Attribute> DODGE_CHANCE = CREATOR.create("dodge_chance");
    public static final ResourceKey<Attribute> INTELLIGENCE = CREATOR.create("intelligence");
}