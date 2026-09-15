package net.ntrdeal.realapi.item.component.type;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ItemEntityListener {
    default void destroyed(ItemEntity entity, ItemStack stack){}
    default void pickup(Player player, ItemEntity entity){}
}
