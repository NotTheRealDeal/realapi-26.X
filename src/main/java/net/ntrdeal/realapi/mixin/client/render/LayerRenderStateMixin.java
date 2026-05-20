package net.ntrdeal.realapi.mixin.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.ntrdeal.realapi.client.render.PreRender;
import net.ntrdeal.realapi.client.render.RealItemModel;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ItemStackRenderState.LayerRenderState.class)
public class LayerRenderStateMixin implements RealMixin<ItemStackRenderState.LayerRenderState> {
    @Shadow @Final ItemStackRenderState this$0;

    @Inject(method = "submit", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER))
    private void ntrdeal$preTransform(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor, CallbackInfo ci) {
        preRender(new PreRender<>(submitNodeCollector, this.this$0, this.getThis(), poseStack, lightCoords, overlayCoords, outlineColor, null), RealItemModel.PRE_TRANSFORM);
    }

    @Inject(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState;applyTransform(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;)V", shift = At.Shift.AFTER))
    private void ntrdeal$postTransform(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor, CallbackInfo ci) {
        preRender(new PreRender<>(submitNodeCollector, this.this$0, this.getThis(), poseStack, lightCoords, overlayCoords, outlineColor, null), RealItemModel.POST_TRANSFORM);
    }

    @Unique
    private static void preRender(PreRender<Void> preRender, int phase) {
        Map<Integer, List<RealItemModel<?>>> modelMap = preRender.state().getData(RealItemModel.RENDERER_KEY);
        if (modelMap == null || modelMap.isEmpty()) return;
        List<RealItemModel<?>> models = modelMap.get(phase);
        if (models == null || models.isEmpty()) return;
        models.forEach(model -> model.preRender(preRender.collector(), preRender.state(), preRender.layer(), preRender.poseStack(), preRender.light(), preRender.overlay(), preRender.outline()));
    }
}
