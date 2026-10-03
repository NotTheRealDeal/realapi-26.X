package net.ntrdeal.realapi.client.render.post;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class RealPostRegistry {
    private RealPostRegistry(){}

    private static final List<PostShader> SHADERS = new ArrayList<>();

    public static void register(PostShader shader) {
        SHADERS.add(shader);
    }

    public static void collect(Consumer<Identifier> render, Entity cameraEntity) {
        for (PostShader shader : SHADERS) {
            Identifier chain = shader.collect(cameraEntity);
            if (chain != null) render.accept(chain);
        }
    }

    @FunctionalInterface
    public interface PostShader {
        @Nullable Identifier collect(Entity cameraEntity);
    }
}
