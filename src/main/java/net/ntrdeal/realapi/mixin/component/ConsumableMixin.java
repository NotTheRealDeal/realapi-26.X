package net.ntrdeal.realapi.mixin.component;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.ntrdeal.realapi.item.component.RealDataComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Consumable.class)
public class ConsumableMixin {
    @WrapOperation(method = "onConsume", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;consume(ILnet/minecraft/world/entity/LivingEntity;)V"))
    private void ntrdeal$preventConsumption(ItemStack stack, int amount, LivingEntity owner, Operation<Void> original) {
        if (!stack.has(RealDataComponents.PREVENT_CONSUMPTION)) original.call(stack, amount, owner);
    }
}
