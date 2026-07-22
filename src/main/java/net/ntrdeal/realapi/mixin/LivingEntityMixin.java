package net.ntrdeal.realapi.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.waypoints.WaypointTransmitter;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import net.ntrdeal.realapi.entity.RealAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, WaypointTransmitter, RealMixin<LivingEntity> {
    @Shadow public abstract boolean isBaby();
    @Shadow public abstract double getAttributeValue(Holder<Attribute> attribute);
    @Shadow public abstract Collection<MobEffectInstance> getActiveEffects();

    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @WrapMethod(method = "createLivingAttributes")
    private static AttributeSupplier.Builder ntrdeal$addAttributes(Operation<AttributeSupplier.Builder> original) {
        return original.call()
                .add(RealAttributes.MOVEMENT_SCALE)
                .add(RealAttributes.ARMOR_PENETRATION)
                .add(RealAttributes.CHARGE_TIME)
                .add(RealAttributes.RANGED_ATTACK_MULTIPLIER)
                .add(RealAttributes.BANE_OF_ADOLESCENCE)
                .add(RealAttributes.FIRE_DAMAGE_MULTIPLIER)
                .add(RealAttributes.DODGE_CHANCE);
    }

    @WrapMethod(method = "removeAllEffects")
    private boolean ntrdeal$syncEffects(Operation<Boolean> original) {
        if (!original.call()) return false;
        if (this.getActiveEffects().isEmpty() || !(this.getThis() instanceof ServerPlayer player)) return true;
        for (MobEffectInstance instance : this.getActiveEffects()) player.connection.send(new ClientboundUpdateMobEffectPacket(
                player.getId(), instance, false
        ));
        return true;
    }

    @WrapOperation(method = "igniteForTicks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/core/Holder;)D"))
    private double ntrdeal$fireDamageMultiplier(LivingEntity entity, Holder<Attribute> attribute, Operation<Double> original) {
        return original.call(entity, attribute) * entity.getAttributeValue(RealAttributes.FIRE_DAMAGE_MULTIPLIER);
    }

    @WrapOperation(method = "calculateFallPower", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/core/Holder;)D"))
    private double ntrdeal$movementScaleFall(LivingEntity entity, Holder<Attribute> attribute, Operation<Double> original) {
        double movementScale = entity.getAttributeValue(RealAttributes.MOVEMENT_SCALE);
        return original.call(entity, attribute) * (movementScale * movementScale);
    }

    @WrapOperation(method = "getJumpPower(F)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/core/Holder;)D"))
    private double ntrdeal$movementScaleJump(LivingEntity entity, Holder<Attribute> attribute, Operation<Double> original) {
        return original.call(entity, attribute) * entity.getAttributeValue(RealAttributes.MOVEMENT_SCALE);
    }

    @WrapMethod(method = "hurtServer")
    private boolean ntrdeal$damageModifiers(ServerLevel level, DamageSource source, float damage, Operation<Boolean> original) {
        double dodgeChance = this.getAttributeValue(RealAttributes.DODGE_CHANCE);
        if (dodgeChance != 0 && this.getRandom().nextDouble() <= dodgeChance) return false;

        boolean fireAffected = source.is(DamageTypeTags.IS_FIRE) && !source.is(DamageTypes.LAVA);
        float fireMulti = (float) this.getAttributeValue(RealAttributes.FIRE_DAMAGE_MULTIPLIER);
        if (fireAffected && fireMulti == 0) return false;

        if (source.getEntity() instanceof LivingEntity entity) {
            if (this.isBaby() || this.entityTags().contains("adolescence")) damage += (float) entity.getAttributeValue(RealAttributes.BANE_OF_ADOLESCENCE);
            if (source.is(DamageTypeTags.IS_PROJECTILE)) damage *= (float) entity.getAttributeValue(RealAttributes.RANGED_ATTACK_MULTIPLIER);
        }

        if (fireAffected) damage *= fireMulti;
        return original.call(level, source, damage);
    }
}
