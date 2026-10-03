package net.ntrdeal.realapi.util.registry;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.UnaryOperator;

public final class CreativeTabRegistry {
    private CreativeTabRegistry(){}

    public static CreativeModeTab register(ResourceKey<CreativeModeTab> key, UnaryOperator<CreativeModeTab.Builder> operator) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, operator.apply(FabricCreativeModeTab.builder()).build());
    }
}