package net.ntrdeal.realapi.data;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface LevelTooltipHolder {
    boolean isBarVisible(Level level, ItemStack stack);
    default int getBarWidth(Level level, ItemStack stack){return stack.getBarWidth();}
    default int getBarColor(Level level, ItemStack stack){return stack.getBarColor();}
}
