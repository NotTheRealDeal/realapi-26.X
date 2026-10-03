package net.ntrdeal.realapi.compat.jei;

import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;

public class RealJeiCompat implements IMixinConfigPlugin {
    public static final boolean LOADED = FabricLoader.getInstance().isModLoaded("jei");

    @Override public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {return LOADED;}
}