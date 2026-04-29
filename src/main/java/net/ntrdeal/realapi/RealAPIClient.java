package net.ntrdeal.realapi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ClientTooltipComponentCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ExtractItemDecorationsCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.item.ItemModels;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.Level;
import net.ntrdeal.realapi.client.event.ContainerScreenEvents;
import net.ntrdeal.realapi.client.event.LevelClientTooltipEvent;
import net.ntrdeal.realapi.client.item.stack_holder.BundleLikeHasIndexed;
import net.ntrdeal.realapi.client.item.stack_holder.BundleLikeSpecialRenderer;
import net.ntrdeal.realapi.client.render.FirstPerson;
import net.ntrdeal.realapi.client.render.LeftHand;
import net.ntrdeal.realapi.client.render.MultiProperty;
import net.ntrdeal.realapi.data.LevelTooltipHolder;
import net.ntrdeal.realapi.item.stack_holder.BundleLikeMouseActions;

public class RealAPIClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTooltipComponentCallback.EVENT.register(component -> {
            if (Minecraft.getInstance().level instanceof ClientLevel level) return LevelClientTooltipEvent.EVENT.invoker().getClientTooltip(level, component);
            else return null;
        });

        ContainerScreenEvents.SLOT_MOUSE_ACTION.register((minecraft, consumer) -> consumer.accept(new BundleLikeMouseActions(minecraft)));

        ExtractItemDecorationsCallback.EVENT.register((graphics, _, stack, x, y) -> {
            if (!stack.isBarVisible() && Minecraft.getInstance().level instanceof Level level && stack.getItem() instanceof LevelTooltipHolder holder && holder.isBarVisible(level, stack)) {
                int left = x + 2;
                int top = y + 13;
                graphics.fill(RenderPipelines.GUI, left, top, left + 13, top + 2, -16777216);
                graphics.fill(RenderPipelines.GUI, left, top, left + holder.getBarWidth(level, stack), top + 1, ARGB.opaque(holder.getBarColor(level, stack)));
            }
        });

        ConditionalItemModelProperties.ID_MAPPER.put(RealAPI.id("multi_property"), MultiProperty.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(RealAPI.id("bundle_like_has"), BundleLikeHasIndexed.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(RealAPI.id("first_person"), FirstPerson.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(RealAPI.id("left_hand"), LeftHand.MAP_CODEC);

        ItemModels.ID_MAPPER.put(RealAPI.id("bundle_like"), BundleLikeSpecialRenderer.Unbaked.MAP_CODEC);
    }
}