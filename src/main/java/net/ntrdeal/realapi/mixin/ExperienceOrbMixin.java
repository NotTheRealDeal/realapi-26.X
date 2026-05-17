package net.ntrdeal.realapi.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import net.ntrdeal.realapi.entity.RealAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExperienceOrb.class)
public abstract class ExperienceOrbMixin extends Entity implements RealMixin<ExperienceOrb> {
    public ExperienceOrbMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @WrapOperation(method = "playerTouch", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;getValue()I"))
    private int ntrdeal$intelligence(ExperienceOrb orb, Operation<Integer> original, Player player) {
        return Math.round(original.call(orb) * (float) player.getAttributeValue(RealAttributes.INTELLIGENCE));
    }
}
