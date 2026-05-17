package net.ntrdeal.realapi.mixin.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.ntrdeal.realapi.client.render.RealItemModel;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemStackRenderState.LayerRenderState.class)
public class LayerRenderStateMixin implements RealMixin<ItemStackRenderState.LayerRenderState> {
    @Shadow @Final ItemStackRenderState this$0;
    @Shadow @Final private List<BakedQuad> quads;
    @Shadow private ItemStackRenderState.FoilType foilType;
    @Shadow @Final public static int[] EMPTY_TINTS;
    @Shadow private @Nullable IntList tintLayers;

    @Inject(method = "submit", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER))
    private void ntrdeal$preTransform(
            PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
            int lightCoords, int overlayCoords, int outlineColor, CallbackInfo ci
    ) {
        List<RealItemModel<?>> models = this.this$0.getDataOrDefault(RealItemModel.RENDERER_KEY, List.of());
        if (models.isEmpty()) return;
        int[] tints = this.tintLayers != null ? this.tintLayers.toArray(EMPTY_TINTS) : EMPTY_TINTS;
        models.forEach(model -> model.useData(
                submitNodeCollector, this.this$0, poseStack, this.this$0.displayContext,
                this.foilType, this.quads, tints, lightCoords, overlayCoords, outlineColor
        ));
    }
}
