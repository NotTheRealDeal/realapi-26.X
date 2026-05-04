package net.ntrdeal.realapi.entity.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.ntrdeal.realapi.data.mixin.ItemPerseverer;
import net.ntrdeal.realapi.item.component.RealDataComponents;
import net.ntrdeal.realapi.tags.RealItemTags;

import java.util.Map;

@FunctionalInterface
public interface KeepOnDeathEvent {
    Event<KeepOnDeathEvent> EVENT = EventFactory.createArrayBacked(KeepOnDeathEvent.class, events -> (player, stack) -> {
        for (KeepOnDeathEvent event : events) {
            if (event.keepOnDeath(player, stack)) return true;
        }
        return false;
    });

    boolean keepOnDeath(Player player, ItemStack stack);

    static void register() {
        EVENT.register((_, stack) -> stack.is(RealItemTags.KEEP_ON_DEATH) || stack.has(RealDataComponents.KEEP_ON_DEATH));

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (!alive) {
                Map<Integer, ItemStack> savedStacks = ((ItemPerseverer)oldPlayer).savedStacks();
                Inventory inventory = newPlayer.getInventory();
                savedStacks.forEach(inventory::add);
            }
        });
    }
}
