package net.ntrdeal.realapi.item.component.type;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ConsumableModifier {
    default boolean decrementsStack(ItemStack stack, LivingEntity entity) {return true;}
    default boolean canConsume(ItemStack stack, LivingEntity entity) {return true;}
}
