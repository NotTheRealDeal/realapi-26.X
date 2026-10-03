package net.ntrdeal.realapi.mixin.recipe;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.ntrdeal.realapi.recipe.RealRecipeEvents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RecipeCraftingHolder.class)
public interface RecipeCraftingHolderMixin {
    @WrapMethod(method = "setRecipeUsed(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/crafting/RecipeHolder;)Z")
    private boolean ntrdeal$requireRecipe(ServerPlayer player, RecipeHolder<?> recipe, Operation<Boolean> original) {
        return RealRecipeEvents.CAN_CRAFT.invoker().canCraft(player, recipe, true) && original.call(player, recipe);
    }
}
