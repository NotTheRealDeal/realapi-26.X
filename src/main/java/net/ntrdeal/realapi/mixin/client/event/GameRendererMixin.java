package net.ntrdeal.realapi.mixin.client.event;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.Identifier;
import net.ntrdeal.realapi.client.render.post.RealPostRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow @Final private Minecraft minecraft;

    @WrapOperation(method = "update", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0))
    private boolean ntrdeal$addPostShaders(List<Identifier> list, Object eof, Operation<Boolean> original) {
        boolean eofAdded = original.call(list, eof);
        RealPostRegistry.collect(list::add, this.minecraft.getCameraEntity());
        return eofAdded;
    }
}
