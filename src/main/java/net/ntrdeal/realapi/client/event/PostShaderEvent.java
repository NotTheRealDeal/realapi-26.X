package net.ntrdeal.realapi.client.event;

import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.CrossFrameResourcePool;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.ShaderManager;
import org.jspecify.annotations.Nullable;

public interface PostShaderEvent {
    Event<PostShaderEvent> EVENT = EventFactory.createArrayBacked(PostShaderEvent.class, events -> (tracker, manager, target, pool) -> {
        for (PostShaderEvent event : events) event.render(tracker, manager, target, pool);
    });

    void render(DeltaTracker tracker, ShaderManager manager, RenderTarget target, CrossFrameResourcePool pool);

    static void renderPostMain(@Nullable PostChain chain, RenderTarget target, GraphicsResourceAllocator allocator) {
        if (chain == null) return;
        FrameGraphBuilder builder = new FrameGraphBuilder();
        PostChain.TargetBundle bundle = PostChain.TargetBundle.of(PostChain.MAIN_TARGET_ID, builder.importExternal("main", target));
        chain.addToFrame(builder, target.width, target.height, bundle);
        builder.execute(allocator);
    }
}
