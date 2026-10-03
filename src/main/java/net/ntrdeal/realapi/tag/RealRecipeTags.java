package net.ntrdeal.realapi.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.recipe.RealRecipeEvents;
import net.ntrdeal.realapi.util.registry.TagCreator;

public final class RealRecipeTags {
    private RealRecipeTags(){}

    private static final TagCreator<Recipe<?>> CREATOR = TagCreator.of(Registries.RECIPE, RealAPI::id);

    public static final TagKey<Recipe<?>> REQUIRE_RECIPE = CREATOR.create("require_recipe");

    public static boolean requiresRecipe(ServerLevel level, RecipeHolder<?> holder) {
        return level.getServer().reloadableRegistries().lookup().get(holder.id()).map(
                recipe -> recipe.is(REQUIRE_RECIPE)
        ).orElse(false);
    }

    public static void register() {
        RealRecipeEvents.CAN_CRAFT.register((
                player, holder, current
        ) -> current && !requiresRecipe(player.level(), holder) || player.getRecipeBook().contains(holder.id()));
    }
}
