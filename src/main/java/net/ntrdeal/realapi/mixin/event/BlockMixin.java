package net.ntrdeal.realapi.mixin.event;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.fabric.api.block.v1.FabricBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.ntrdeal.realapi.block.event.RealBlockBreakingEvents;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockMixin extends BlockBehaviour implements ItemLike, FabricBlock, RealMixin<Block> {
    public BlockMixin(Properties properties) {
        super(properties);
    }

    @WrapMethod(method = "dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V")
    private static void ntrdeal$blockEvent(BlockState state, Level level, BlockPos pos, BlockEntity blockEntity, Entity breaker, ItemStack tool, Operation<Void> original) {
        if (RealBlockBreakingEvents.DROP_RESOURCES.invoker().drop(state, level, pos, blockEntity, breaker, tool)) original.call(state, level, pos, blockEntity, breaker, tool);
    }
}
