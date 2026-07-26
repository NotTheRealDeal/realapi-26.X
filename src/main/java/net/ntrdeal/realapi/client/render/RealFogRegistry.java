package net.ntrdeal.realapi.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public final class RealFogRegistry {
    private RealFogRegistry(){}

    public static void registerBeforeClass(FogEnvironment environment, @Nullable Class<? extends FogEnvironment> anchor) {
        if (anchor == null) {
            FogRenderer.FOG_ENVIRONMENTS.addFirst(environment);
            return;
        }

        for (int index = 0; index < FogRenderer.FOG_ENVIRONMENTS.size(); index++) {
            FogEnvironment point = FogRenderer.FOG_ENVIRONMENTS.get(index);
            if (!anchor.isInstance(point)) continue;
            FogRenderer.FOG_ENVIRONMENTS.add(index, environment);
            return;
        }
    }
}
