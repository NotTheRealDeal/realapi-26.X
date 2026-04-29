package net.ntrdeal.realapi.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.recipebook.PlaceRecipeHelper;
import net.minecraft.world.item.crafting.Recipe;
import net.ntrdeal.realapi.data.RecipeDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlaceRecipeHelper.class)
public interface PlaceRecipeHelperMixin {
    @WrapOperation(method = "placeRecipe(IILnet/minecraft/world/item/crafting/Recipe;Ljava/lang/Iterable;Lnet/minecraft/recipebook/PlaceRecipeHelper$Output;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipebook/PlaceRecipeHelper;placeRecipe(IIIILjava/lang/Iterable;Lnet/minecraft/recipebook/PlaceRecipeHelper$Output;)V", ordinal = 1))
    private static <T> void ntrdeal$recipeDimensions(int gridWidth, int gridHeight, int recipeWidth, int recipeHeight, Iterable<T> entries, PlaceRecipeHelper.Output<T> output, Operation<Void> original, @Local(argsOnly = true, name = "recipe") Recipe<?> recipe) {
        if (recipe instanceof RecipeDimensions dimensions) {
            original.call(gridWidth, gridHeight, dimensions.getWidth(), dimensions.getHeight(), entries, output);
        } else original.call(gridWidth, gridHeight, recipeWidth, recipeHeight, entries, output);
    }
}