package net.ntrdeal.realapi.mixin.event;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import net.ntrdeal.realapi.entity.event.PlayerPickupItemEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements TraceableEntity, RealMixin<ItemEntity> {
    public ItemEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @WrapOperation(method = "playerTouch", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;getItem()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack ntrdeal$replaceItem(ItemEntity entity, Operation<ItemStack> original, Player player) {
        ItemStack stack = original.call(entity);
        return PlayerPickupItemEvent.REPLACE_ITEM.invoker().replaceItem(player, entity, stack);
    }

    @WrapOperation(method = "playerTouch", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z"))
    private boolean ntrdeal$pickupEvent(Inventory inventory, ItemStack itemStack, Operation<Boolean> original) {
        return PlayerPickupItemEvent.CAN_PICKUP.invoker().canPickup(inventory.player, this.getThis(), itemStack, true) && original.call(inventory, itemStack);
    }
}