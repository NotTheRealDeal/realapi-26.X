package net.ntrdeal.realapi.mixin.component;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.ntrdeal.realapi.item.component.type.ConsumableModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Consumable.class)
public class ConsumableMixin {
    @WrapOperation(method = "onConsume", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;consume(ILnet/minecraft/world/entity/LivingEntity;)V"))
    private void ntrdeal$preventConsumption(ItemStack stack, int amount, LivingEntity owner, Operation<Void> original) {
        for (ConsumableModifier modifier : stack.getAllOfClass(ConsumableModifier.class)) if (!modifier.decrementsStack(stack, owner)) return;
        original.call(stack, amount, owner);
    }

    @WrapMethod(method = "canConsume")
    private boolean ntrdeal$preventConsumption(LivingEntity user, ItemStack stack, Operation<Boolean> original) {
        for (ConsumableModifier modifier : stack.getAllOfClass(ConsumableModifier.class)) if (!modifier.canConsume(stack, user)) return false;
        return original.call(user, stack);
    }
}
