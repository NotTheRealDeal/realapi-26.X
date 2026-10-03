package net.ntrdeal.realapi.mixin.recipe;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.CrafterBlock;
import net.ntrdeal.realapi.tag.RealRecipeTags;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(CrafterBlock.class)
public class CrafterBlockMixin {
    @WrapMethod(method = "getPotentialResults")
    private static Optional<RecipeHolder<CraftingRecipe>> ntrdeal$require_recipe(
            ServerLevel level, CraftingInput input,
            Operation<Optional<RecipeHolder<CraftingRecipe>>> original
    ) {
        return original.call(level, input).map(holder -> RealRecipeTags.requiresRecipe(level, holder) ? null : holder);
    }
}
