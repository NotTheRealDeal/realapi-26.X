package net.ntrdeal.realapi.mixin.client.event;

import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.ntrdeal.realapi.client.event.SetAnglesEvents;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends HumanoidRenderState> extends EntityModel<T> implements ArmedModel<T>, HeadedModel, RealMixin<HumanoidModel<HumanoidRenderState>> {
    protected HumanoidModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At("TAIL"))
    private void ntrdeal$setAngles(T state, CallbackInfo ci) {
        SetAnglesEvents.HUMANOID_ANGLES.invoker().setAngles(state, this.getThis());
    }
}
