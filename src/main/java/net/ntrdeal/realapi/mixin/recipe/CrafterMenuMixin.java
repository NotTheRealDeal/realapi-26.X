package net.ntrdeal.realapi.mixin.recipe;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CrafterMenu;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.ntrdeal.realapi.recipe.RealRecipeEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(CrafterMenu.class)
public class CrafterMenuMixin {
    @Shadow @Final private Player player;

//    @WrapOperation(method = "refreshRecipeResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/CrafterBlock;getPotentialResults(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/crafting/CraftingInput;)Ljava/util/Optional;"))
//    private Optional<RecipeHolder<CraftingRecipe>> ntrdeal$requireRecipe(
//            ServerLevel level, CraftingInput input, Operation<Optional<RecipeHolder<CraftingRecipe>>> original
//    ) {
//        return original.call(level, input).map(holder ->
//            RealRecipeEvents.CAN_CRAFT.invoker().canCraft((ServerPlayer) this.player, holder, true) ? holder : null
//        );
//    }
}
