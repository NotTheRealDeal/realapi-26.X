package net.ntrdeal.realapi.item.component.type;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface PostHurtListener {
    void postHurt(ItemStack stack, LivingEntity attacker, LivingEntity attacked);
}
