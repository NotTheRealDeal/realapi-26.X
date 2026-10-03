package net.ntrdeal.realapi.mixin.event;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.waypoints.WaypointTransmitter;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import net.ntrdeal.realapi.entity.event.RealLivingEntityEvents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, WaypointTransmitter, RealMixin<LivingEntity> {
    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @WrapMethod(method = "isInvulnerableTo")
    private boolean ntrdeal$invulnerableEvent(ServerLevel level, DamageSource source, Operation<Boolean> original) {
        return original.call(level, source) || RealLivingEntityEvents.INVULNERABLE_TO.invoker().isInvulnerableTo(this.getThis(), source);
    }
}
