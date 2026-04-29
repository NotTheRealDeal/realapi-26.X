package net.ntrdeal.realapi.client.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.jspecify.annotations.Nullable;

public interface LevelClientTooltipEvent {
    Event<LevelClientTooltipEvent> EVENT = EventFactory.createArrayBacked(LevelClientTooltipEvent.class, events -> (level, component) -> {
        for (LevelClientTooltipEvent event : events) {
            ClientTooltipComponent tooltip = event.getClientTooltip(level, component);
            if (tooltip != null) return tooltip;
        }
        return null;
    });

    @Nullable
    ClientTooltipComponent getClientTooltip(ClientLevel level, TooltipComponent component);
}
