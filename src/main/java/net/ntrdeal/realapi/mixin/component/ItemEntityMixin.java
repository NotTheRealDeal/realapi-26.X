package net.ntrdeal.realapi.mixin.component;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import net.ntrdeal.realapi.item.component.type.ItemEntityListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin implements RealMixin<ItemEntity> {
    @Inject(method = "playerTouch", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;take(Lnet/minecraft/world/entity/Entity;I)V"))
    private void ntrdeal$pickup(Player player, CallbackInfo ci) {
        ItemEntity entity = this.getThis();
        entity.getItem().runAllOfClass(ItemEntityListener.class, listener -> listener.pickup(player, entity));
    }
}
