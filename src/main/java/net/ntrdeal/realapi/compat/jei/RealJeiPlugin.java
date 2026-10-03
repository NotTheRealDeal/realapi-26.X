package net.ntrdeal.realapi.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.ntrdeal.realapi.RealAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class RealJeiPlugin implements IRealModPlugin {
    public static final Identifier PLUGIN_ID = RealAPI.id("real_compat");

    @Override public Identifier getPluginUid() {return PLUGIN_ID;}

    @Override
    public void postRuntime(IJeiRuntime runtime) {
        IRecipeManager recipeManager = runtime.getRecipeManager();
        IIngredientManager ingredientManager = runtime.getIngredientManager();
        IFocusFactory focusFactory = runtime.getJeiHelpers().getFocusFactory();
        IFocusGroup emptyFocus = focusFactory.getEmptyFocusGroup();

        Collection<IRecipeCategory<?>> categories = recipeManager.createRecipeCategoryLookup().includeHidden().get().toList();

        if (!categories.isEmpty()) {
            Predicate<ItemStack> predicate = RealJeiEvents.HIDE_OUTPUT.invoker().getPredicate(_ -> false);

            for (IRecipeCategory<?> category : categories) {
                hideCategoriesOutputs(recipeManager, category, emptyFocus, predicate);
            }
        }

        Collection<ItemStack> sidebar = ingredientManager.getAllItemStacks().stream().filter(
                RealJeiEvents.HIDE_SIDEBAR.invoker().getPredicate(_ -> false)
        ).toList();

        if (!sidebar.isEmpty()) ingredientManager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, sidebar);
    }

    private static <T> void hideCategoriesOutputs(
            IRecipeManager manager, IRecipeCategory<T> category,
            IFocusGroup focuses, Predicate<ItemStack> predicate
    ) {
        List<T> recipes = manager.createRecipeLookup(category.getRecipeType()).get().toList();
        if (recipes.isEmpty()) return;
        List<T> hide = new ArrayList<>();

        for (T recipe : recipes) {
            try {
                if (manager.createRecipeLayoutDrawable(category, recipe, focuses).map(layout ->
                        layout.getRecipeSlotsView().getSlotViews(RecipeIngredientRole.OUTPUT).stream().flatMap(
                                IRecipeSlotView::getItemStacks
                        ).anyMatch(predicate)
                ).orElse(false)) hide.add(recipe);
            } catch (Exception _){}
        }

        if (!hide.isEmpty()) manager.hideRecipes(category.getRecipeType(), hide);
    }
}
