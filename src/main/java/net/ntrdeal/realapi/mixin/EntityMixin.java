package net.ntrdeal.realapi.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import net.ntrdeal.realapi.entity.RealAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin implements RealMixin<Entity> {
    @WrapOperation(method = "moveRelative", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getInputVector(Lnet/minecraft/world/phys/Vec3;FF)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 ntrdeal$movementScale(Vec3 input, float speed, float yRot, Operation<Vec3> original) {
        if ((Entity)(Object)this instanceof LivingEntity entity) return original.call(input, speed * (float) entity.getAttributeValue(RealAttributes.MOVEMENT_SCALE), yRot);
        else return original.call(input, speed, yRot);
    }
}
