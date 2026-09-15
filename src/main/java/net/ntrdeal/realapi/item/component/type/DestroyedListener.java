package net.ntrdeal.realapi.item.component.type;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public interface DestroyedListener {
    void destroyed(ItemEntity entity, ItemStack stack);
}
