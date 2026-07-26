package net.ntrdeal.realapi.item.component.type;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface EquipmentChangeListener {
    default void equipped(ItemStack equipped, LivingEntity entity, EquipmentSlot slot, ItemStack unequipped) {}
    default void unequipped(ItemStack unequipped, LivingEntity entity, EquipmentSlot slot, ItemStack equipped) {}
}
