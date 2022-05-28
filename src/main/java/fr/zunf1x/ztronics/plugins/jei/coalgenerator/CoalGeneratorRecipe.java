package fr.zunf1x.ztronics.plugins.jei.coalgenerator;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class CoalGeneratorRecipe implements IRecipeWrapper {

    private final ItemStack input;

    public CoalGeneratorRecipe(ItemStack input) {
        this.input = input;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, input);
    }
}
