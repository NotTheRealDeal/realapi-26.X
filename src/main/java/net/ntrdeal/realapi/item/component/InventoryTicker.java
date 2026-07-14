package net.ntrdeal.realapi.item.component;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public interface InventoryTicker {
    void tick(ItemStack stack, Level level, Entity owner, @Nullable EquipmentSlot slot);
}
