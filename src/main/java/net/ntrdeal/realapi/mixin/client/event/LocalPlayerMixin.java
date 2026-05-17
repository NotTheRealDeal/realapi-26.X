package net.ntrdeal.realapi.mixin.client.event;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import net.ntrdeal.realapi.item.events.ClientItemEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer implements RealMixin<LocalPlayer> {
    public LocalPlayerMixin(ClientLevel level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Inject(method = "openItemGui", at = @At("TAIL"))
    private void ntrdeal$openGuiEvent(ItemStack itemStack, InteractionHand hand, CallbackInfo ci) {
        ClientItemEvents.OPEN_GUI.invoker().open(this.getThis(), itemStack, hand);
    }
}