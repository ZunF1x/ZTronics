package fr.zunf1x.ztronics.plugins.jei.coalgenerator;

import fr.zunf1x.ztronics.plugins.jei.RecipeCategories;
import fr.zunf1x.ztronics.utils.Constants;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

public class CoalGeneratorRecipeCategory extends AbstractCoalGeneratorRecipeCategory<CoalGeneratorRecipe> {

    private final IDrawable background;
    private final String name;

    public CoalGeneratorRecipeCategory(IGuiHelper helper) {
        super(helper);

        background = helper.createDrawable(TEXTURES, 4, 4, 168, 78);
        name = "Coal Generator";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        animatedFlame.draw(minecraft, 77, 51);
        animatedEnergy.draw(minecraft, 5, 18);
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getModName() {
        return Constants.NAME;
    }

    @Override
    public String getUid() {
        return RecipeCategories.COAL_GENERATOR;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CoalGeneratorRecipe recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(fuel, true, 75, 30);
        stacks.set(ingredients);
    }
}
