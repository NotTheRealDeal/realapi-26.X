package net.ntrdeal.realapi.data.mixin;

import net.minecraft.world.item.ItemStack;

import java.util.Map;

public interface ItemPerseverer {
    Map<Integer, ItemStack> savedStacks();
}
