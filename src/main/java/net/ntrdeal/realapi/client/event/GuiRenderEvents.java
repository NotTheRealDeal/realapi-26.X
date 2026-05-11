package net.ntrdeal.realapi.client.event;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public interface GuiRenderEvents {
    Event<ReplaceCrosshairEvent> REPLACE_CROSSHAIR = EventFactory.createArrayBacked(ReplaceCrosshairEvent.class, events -> (minecraft, currentCrosshair) -> {
        for (ReplaceCrosshairEvent event : events) {
            Crosshair newCrosshair = event.replace(minecraft, currentCrosshair);
            if (newCrosshair != null && !newCrosshair.equals(currentCrosshair)) return newCrosshair;
        }
        return currentCrosshair;
    });

    Event<CrosshairAdditionEvent> CROSSHAIR_ADDITION = EventFactory.createArrayBacked(CrosshairAdditionEvent.class, events -> (minecraft, graphics, deltaTracker, crosshair, x, y) -> {
        for (CrosshairAdditionEvent event : events) {
            event.add(minecraft, graphics, deltaTracker, crosshair, x, y);
        }
    });

    Event<ReplaceAttackIndicatorEvent> REPLACE_ATTACK_INDICATOR = EventFactory.createArrayBacked(ReplaceAttackIndicatorEvent.class, events -> (minecraft, defaultPack, crosshair) -> {
        for (ReplaceAttackIndicatorEvent event : events) {
            AttackIndicator pack = event.replace(minecraft, defaultPack, crosshair);
            if (pack != null && !pack.equals(defaultPack)) return pack;
        }
        return defaultPack;
    });

    @FunctionalInterface
    interface ReplaceCrosshairEvent {
        @Nullable Crosshair replace(Minecraft minecraft, Crosshair currentCrosshair);
    }

    @FunctionalInterface
    interface CrosshairAdditionEvent {
        void add(Minecraft minecraft, GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, Crosshair crosshair, int x, int y);
    }

    @FunctionalInterface
    interface ReplaceAttackIndicatorEvent {
        GuiRenderEvents.@Nullable AttackIndicator replace(Minecraft minecraft, AttackIndicator defaultPack, Crosshair crosshair);
    }

    record Crosshair(
            RenderPipeline pipeline,
            Identifier texture,
            Function<Integer, Integer> guiWidthToX,
            Function<Integer, Integer> guiHeightToY,
            int width,
            int height
    ) {
        public static final Crosshair DEFAULT = Crosshair.ofMiddle(Identifier.withDefaultNamespace("hud/crosshair"), 15, 15);

        public static Crosshair ofDefaultPipeline(
                Identifier texture,
                Function<Integer, Integer> guiWidthToX,
                Function<Integer, Integer> guiHeightToY,
                int width,
                int height
        ) {
            return new Crosshair(RenderPipelines.CROSSHAIR, texture, guiWidthToX, guiHeightToY, width, height);
        }

        public static Crosshair ofMiddle(Identifier texture, int width, int height) {
            return Crosshair.ofDefaultPipeline(
                    texture,
                    guiWidth -> (guiWidth - width) / 2,
                    guiHeight -> (guiHeight - height) / 2,
                    width, height
            );
        }
    }

    record AttackIndicator(Identifier full, Identifier background, Identifier progress) {
        public static final AttackIndicator DEFAULT = new AttackIndicator(
                Identifier.withDefaultNamespace("hud/crosshair_attack_indicator_full"),
                Identifier.withDefaultNamespace("hud/crosshair_attack_indicator_background"),
                Identifier.withDefaultNamespace("hud/crosshair_attack_indicator_progress")
        );

        @Override
        public boolean equals(Object obj) {
            return obj instanceof AttackIndicator(Identifier objFull, Identifier objBackground, Identifier objProgress) &&
                    objFull.equals(this.full) && objBackground.equals(this.background) && objProgress.equals(this.progress);
        }
    }
}
