package net.ntrdeal.realapi.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.ntrdeal.realapi.entity.RealAttributes;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CombatRules.class)
public abstract class CombatRulesMixin {
    @WrapMethod(method = "getDamageAfterAbsorb")
    private static float ntrdeal$armorPenetration(LivingEntity victim, float damage, DamageSource source, float totalArmor, float armorToughness, Operation<Float> original) {
        Entity causing = source.getEntity();
        if (causing == null) return original.call(victim, damage, source, totalArmor, armorToughness);

        if (!causing.is(EntityTypes.PLAYER)) totalArmor += (float) victim.getAttributeValue(RealAttributes.NPC_ARMOR);
        if (causing instanceof LivingEntity entity) totalArmor -= (float) entity.getAttributeValue(RealAttributes.ARMOR_PENETRATION);

        return original.call(victim, damage, source, Math.max(totalArmor, 0), armorToughness);
    }
}
