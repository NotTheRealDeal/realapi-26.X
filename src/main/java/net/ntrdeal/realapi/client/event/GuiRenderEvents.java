package net.ntrdeal.realapi.client.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

public interface GuiRenderEvents {
    Event<ReplaceCrosshairEvent> REPLACE_CROSSHAIR = EventFactory.createArrayBacked(ReplaceCrosshairEvent.class, events -> (minecraft, crosshair) -> {
        for (ReplaceCrosshairEvent event : events) {
            Identifier newTexture = event.replace(minecraft, crosshair);
            if (newTexture != null && !newTexture.equals(crosshair)) return newTexture;
        }
        return crosshair;
    });

    Event<CrosshairAdditionEvent> CROSSHAIR_ADDITION = EventFactory.createArrayBacked(CrosshairAdditionEvent.class, events -> (minecraft, graphics, deltaTracker, crosshair, x, y, width, height) -> {
        for (CrosshairAdditionEvent event : events) {
            event.add(minecraft, graphics, deltaTracker, crosshair, x, y ,width, height);
        }
    });

    Event<ReplaceAttackIndicatorEvent> REPLACE_ATTACK_INDICATOR = EventFactory.createArrayBacked(ReplaceAttackIndicatorEvent.class, events -> (minecraft, defaultPack, crosshair) -> {
        for (ReplaceAttackIndicatorEvent event : events) {
            AttackIndicatorPack pack = event.replace(minecraft, defaultPack, crosshair);
            if (pack != null && !pack.equals(defaultPack)) return pack;
        }
        return defaultPack;
    });

    @FunctionalInterface
    interface ReplaceCrosshairEvent {
        @Nullable Identifier replace(Minecraft minecraft, Identifier crosshair);
    }

    @FunctionalInterface
    interface CrosshairAdditionEvent {
        void add(Minecraft minecraft, GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, Identifier crosshair, int x, int y, int width, int height);
    }

    @FunctionalInterface
    interface ReplaceAttackIndicatorEvent {
        @Nullable AttackIndicatorPack replace(Minecraft minecraft, AttackIndicatorPack defaultPack, Identifier crosshair);
    }

    record AttackIndicatorPack(Identifier full, Identifier background, Identifier progress) {
        public static final AttackIndicatorPack DEFAULT = new AttackIndicatorPack(
                Identifier.withDefaultNamespace("hud/crosshair_attack_indicator_full"),
                Identifier.withDefaultNamespace("hud/crosshair_attack_indicator_background"),
                Identifier.withDefaultNamespace("hud/crosshair_attack_indicator_progress")
        );

        @Override
        public boolean equals(Object obj) {
            return obj instanceof AttackIndicatorPack(Identifier full1, Identifier background1, Identifier progress1)
                    && full1.equals(this.full) && background1.equals(this.background) && progress1.equals(this.progress);
        }
    }
}
