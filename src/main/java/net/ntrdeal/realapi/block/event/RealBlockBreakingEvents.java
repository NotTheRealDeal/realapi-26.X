package net.ntrdeal.realapi.block.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public final class RealBlockBreakingEvents {
    private RealBlockBreakingEvents(){}

    public static final Event<DropResourcesEvent> DROP_RESOURCES = EventFactory.createArrayBacked(DropResourcesEvent.class, events -> (state, level, pos, blockEntity, entity, stack) -> {
        for (DropResourcesEvent event : events) {
            if (event.drop(state, level, pos, blockEntity, entity, stack)) return false;
        }

        return true;
    });

    @FunctionalInterface
    public interface DropResourcesEvent {
        boolean drop(BlockState state, Level level, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack stack);
    }
}
