package net.ntrdeal.realapi.mixin.component;

import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import net.ntrdeal.realapi.item.component.type.ClassToTypeHolder;
import net.ntrdeal.realapi.item.component.type.InventoryTicker;
import net.ntrdeal.realapi.item.component.type.ItemEntityListener;
import net.ntrdeal.realapi.item.component.type.PostHurtListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder, ItemInstance, ClassToTypeHolder, RealMixin<ItemStack> {
    @Shadow @Final private PatchedDataComponentMap components;
    @Shadow public abstract boolean isEmpty();

    @Inject(method = "inventoryTick", at = @At("RETURN"))
    private void ntrdeal$tick(Level level, Entity owner, EquipmentSlot slot, CallbackInfo ci) {
        ItemStack stack = this.getThis();
        this.runAllOfClass(InventoryTicker.class, ticker -> ticker.tick(stack, level, owner, slot));
    }

    @Inject(method = "postHurtEnemy", at = @At("RETURN"))
    private void ntrdeal$postHurt(LivingEntity mob, LivingEntity attacker, CallbackInfo ci) {
        ItemStack stack = this.getThis();
        this.runAllOfClass(PostHurtListener.class, listener -> listener.postHurt(stack, attacker, mob));
    }

    @Inject(method = "onDestroyed", at = @At("RETURN"))
    private void ntrdeal$destroyed(ItemEntity itemEntity, CallbackInfo ci) {
        ItemStack stack = this.getThis();
        this.runAllOfClass(ItemEntityListener.class, listener -> listener.destroyed(itemEntity, stack));
    }

    @Override
    public <T> void runAllOfClass(Class<? extends T> clazz, Consumer<T> consumer) {
        if (!this.isEmpty()) for (DataComponentType<? extends T> type : this.components.requestTypes(clazz)) consumer.accept(this.components.get(type));
    }
}
