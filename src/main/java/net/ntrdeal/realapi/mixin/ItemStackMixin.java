package net.ntrdeal.realapi.mixin;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import net.ntrdeal.realapi.item.component.InventoryTicker;
import net.ntrdeal.realapi.util.ItemStackUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements RealMixin<ItemStack> {
    @Shadow public abstract boolean isEmpty();
    @Shadow @Final private PatchedDataComponentMap components;

    @Unique private Set<DataComponentType<? extends InventoryTicker>> tickingComponents;
    @Unique private int mapCode = 0;
    @Unique private boolean initialized = false;

    @Inject(method = "inventoryTick", at = @At("RETURN"))
    private void ntrdeal$tickCommon(Level level, Entity owner, EquipmentSlot slot, CallbackInfo ci) {
        if (this.isEmpty()) return;
        ItemStack stack = this.getThis();
        int newMapCode = this.components.keySet().hashCode();

        if (!initialized || this.mapCode != newMapCode) {
            if (this.tickingComponents == null) this.tickingComponents = new HashSet<>();
            else this.tickingComponents.clear();
            ItemStackUtil.getAllOfType(stack, InventoryTicker.class).map(TypedDataComponent::type).forEach(this.tickingComponents::add);
            this.mapCode = newMapCode;
            this.initialized = true;
        }

        for (DataComponentType<? extends InventoryTicker> type : this.tickingComponents) {
            InventoryTicker ticker = stack.get(type);
            if (ticker != null) ticker.tick(stack, level, owner, slot);
        }
    }
}
