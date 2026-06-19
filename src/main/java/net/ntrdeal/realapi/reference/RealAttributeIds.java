package net.ntrdeal.realapi.reference;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.ntrdeal.realapi.RealAPI;

public final class RealAttributeIds {
    public static final ResourceKey<Attribute> MOVEMENT_SCALE = create("movement_scale");
    public static final ResourceKey<Attribute> SHIELD_FRAGILITY = create("shield_fragility");
    public static final ResourceKey<Attribute> ARMOR_PENETRATION = create("armor_penetration");
    public static final ResourceKey<Attribute> APPETITE = create("appetite");
    public static final ResourceKey<Attribute> CHARGE_TIME = create("charge_time");
    public static final ResourceKey<Attribute> RANGED_ATTACK_MULTIPLIER = create("ranged_attack_multiplier");
    public static final ResourceKey<Attribute> BANE_OF_ADOLESCENCE = create("bane_of_adolescence");
    public static final ResourceKey<Attribute> FIRE_DAMAGE_MULTIPLIER = create("fire_damage_multiplier");
    public static final ResourceKey<Attribute> DODGE_CHANCE = create("dodge_chance");
    public static final ResourceKey<Attribute> INTELLIGENCE = create("intelligence");


    private static ResourceKey<Attribute> create(String name) {
        return ResourceKey.create(Registries.ATTRIBUTE, RealAPI.id(name));
    }
}
