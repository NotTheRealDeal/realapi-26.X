package net.ntrdeal.realapi.mixin;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import net.ntrdeal.realapi.item.component.InventoryTicker;
import net.ntrdeal.realapi.util.ItemStackUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements RealMixin<ItemStack> {
    @Unique private Set<DataComponentType<? extends InventoryTicker>> tickingComponents;
    @Unique private int keyHash = 0;
    @Unique private boolean initialized = false;

    @Inject(method = "inventoryTick", at = @At("RETURN"))
    private void ntrdeal$tick(Level level, Entity owner, EquipmentSlot slot, CallbackInfo ci) {
        ItemStack stack = this.getThis();
        if (stack.isEmpty()) return;
        int newHash = stack.getComponents().keySet().hashCode();

        if (!this.initialized || this.keyHash != newHash) {
            if (this.tickingComponents == null) this.tickingComponents = new HashSet<>();
            else this.tickingComponents.clear();
            ItemStackUtil.getAllOfType(stack, InventoryTicker.class).map(TypedDataComponent::type).forEach(this.tickingComponents::add);
            this.keyHash = newHash;
            this.initialized = true;
        }

        for (DataComponentType<? extends InventoryTicker> type : this.tickingComponents) {
            InventoryTicker ticker = stack.get(type);
            if (ticker != null) ticker.tick(stack, level, owner, slot);
        }
    }
}
