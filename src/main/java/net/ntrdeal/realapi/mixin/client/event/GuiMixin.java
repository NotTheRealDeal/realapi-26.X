package net.ntrdeal.realapi.mixin.client.event;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.ntrdeal.realapi.client.event.GuiRenderEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.class)
public class GuiMixin {
    @Shadow @Final private Minecraft minecraft;

    @Unique private GuiRenderEvents.AttackIndicatorPack pack = GuiRenderEvents.AttackIndicatorPack.DEFAULT;

    @WrapOperation(method = "extractCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 0))
    private void ntrdeal$guiRenderEvents(GuiGraphicsExtractor graphics, RenderPipeline renderPipeline, Identifier location, int x, int y, int width, int height, Operation<Void> original, @Local(argsOnly = true, name = "deltaTracker") DeltaTracker deltaTracker) {
        Identifier crosshair = GuiRenderEvents.REPLACE_CROSSHAIR.invoker().replace(this.minecraft, location);
        original.call(graphics, renderPipeline, crosshair, x, y, width, height);
        GuiRenderEvents.CROSSHAIR_ADDITION.invoker().add(this.minecraft, graphics, deltaTracker, crosshair, x, y, width, height);
        this.pack = GuiRenderEvents.REPLACE_ATTACK_INDICATOR.invoker().replace(this.minecraft, GuiRenderEvents.AttackIndicatorPack.DEFAULT, crosshair);
    }

    @WrapOperation(method = "extractCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal =  1 ))
    private void ntrdeal$indicatorFull(GuiGraphicsExtractor graphics, RenderPipeline renderPipeline, Identifier location, int x, int y, int width, int height, Operation<Void> original) {
        original.call(graphics, renderPipeline, this.pack.full(), x, y, width, height);
    }

    @WrapOperation(method = "extractCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIII)V"))
    private void ntrdeal$indicatorProgress(GuiGraphicsExtractor graphics, RenderPipeline renderPipeline, Identifier location, int spriteWidth, int spriteHeight, int textureX, int textureY, int x, int y, int width, int height, Operation<Void> original) {
        original.call(graphics, renderPipeline, this.pack.progress(), spriteWidth, spriteHeight, textureX, textureY, x, y, width, height);
    }

    @WrapOperation(method = "extractCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal =  2))
    private void ntrdeal$indicatorBackground(GuiGraphicsExtractor graphics, RenderPipeline renderPipeline, Identifier location, int x, int y, int width, int height, Operation<Void> original) {
        original.call(graphics, renderPipeline, this.pack.background(), x, y, width, height);
    }
}
