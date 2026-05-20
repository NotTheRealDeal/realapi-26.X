package net.ntrdeal.realapi.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.jspecify.annotations.Nullable;

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