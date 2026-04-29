package net.ntrdeal.realapi.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.Level;
import net.ntrdeal.realapi.entity.RealAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin extends Avatar implements ContainerUser {
    protected PlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @WrapMethod(method = "createAttributes")
    private static AttributeSupplier.Builder ntrdeal$addAttributes(Operation<AttributeSupplier.Builder> original) {
        return original.call()
                .add(RealAttributes.SHIELD_FRAGILITY)
                .add(RealAttributes.APPETITE)
                .add(RealAttributes.INTELLIGENCE);
    }

    @WrapOperation(method = "causeFoodExhaustion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V"))
    private void ntrdeal$appetite(FoodData data, float amount, Operation<Void> original) {
        original.call(data, amount * (float) this.getAttributeValue(RealAttributes.APPETITE));
    }

    @WrapOperation(method = "blockUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getSecondsToDisableBlocking()F"))
    private float ntrdeal$shieldFragility(LivingEntity entity, Operation<Float> original) {
        return original.call(entity) * (float) this.getAttributeValue(RealAttributes.SHIELD_FRAGILITY);
    }
}