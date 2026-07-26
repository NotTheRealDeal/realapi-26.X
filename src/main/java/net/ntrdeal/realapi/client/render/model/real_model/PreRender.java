package net.ntrdeal.realapi.client.render.model.real_model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record PreRender<T>(
        SubmitNodeCollector collector,
        ItemStackRenderState state,
        ItemStackRenderState.LayerRenderState layer,
        PoseStack poseStack,
        int light,
        int overlay,
        int outline,
        @Nullable T data
){}