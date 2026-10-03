package net.ntrdeal.realapi.mixin.client.render;

import it.unimi.dsi.fastutil.floats.FloatFloatPair;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.ntrdeal.realapi.client.render.RealFogRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FogRenderer.class, priority = 999)
public class FogRendererMixin {
    @Inject(method = "setupFog", at = @At("RETURN"))
    private void ntrdeal$overrideDistance(
            Camera camera, int renderDistanceInChunks, DeltaTracker deltaTracker,
            float darkenWorldAmount, ClientLevel level, CallbackInfoReturnable<FogData> cir
    ) {
        FogData data = cir.getReturnValue();
        FloatFloatPair pair = data.getData(RealFogRegistry.OVERRIDE_DISTANCE);
        if (pair == null) return;
        data.renderDistanceStart = pair.firstFloat();
        data.renderDistanceEnd = pair.secondFloat();
    }
}
