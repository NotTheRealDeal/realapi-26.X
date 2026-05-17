package net.ntrdeal.realapi.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import net.ntrdeal.realapi.entity.RealAttributes;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CombatRules.class)
public abstract class CombatRulesMixin implements RealMixin<CombatRules> {
    @WrapMethod(method = "getDamageAfterAbsorb")
    private static float ntrdeal$armorPenetration(LivingEntity victim, float damage, DamageSource source, float totalArmor, float armorToughness, Operation<Float> original) {
        if (source.getEntity() instanceof LivingEntity entity) {
            return original.call(victim, damage, source, totalArmor - (float) entity.getAttributeValue(RealAttributes.ARMOR_PENETRATION), armorToughness);
        } else return original.call(victim, damage, source, totalArmor, armorToughness);
    }
}
