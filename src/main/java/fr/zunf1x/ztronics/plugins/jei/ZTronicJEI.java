package fr.zunf1x.ztronics.plugins.jei;

import fr.zunf1x.ztronics.blocks.BlockCoalGenerator;
import fr.zunf1x.ztronics.init.ModBlocks;
import fr.zunf1x.ztronics.machines.coalgenerator.ContainerCoalGenerator;
import fr.zunf1x.ztronics.machines.coalgenerator.GuiCoalGenerator;
import fr.zunf1x.ztronics.plugins.jei.coalgenerator.AbstractCoalGeneratorRecipeCategory;
import fr.zunf1x.ztronics.plugins.jei.coalgenerator.CoalGeneratorRecipeCategory;
import fr.zunf1x.ztronics.plugins.jei.coalgenerator.CoalGeneratorRecipeMaker;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import java.util.IllegalFormatException;

@JEIPlugin
public class ZTronicJEI implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers helpers = registry.getJeiHelpers();
        IGuiHelper gui = helpers.getGuiHelper();

        registry.addRecipeCategories(new CoalGeneratorRecipeCategory(gui));
    }

    @Override
    public void register(IModRegistry registry) {
        IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IRecipeTransferRegistry recipeTransfer = registry.getRecipeTransferRegistry();

        registry.addRecipes(CoalGeneratorRecipeMaker.getRecipes(jeiHelpers), RecipeCategories.COAL_GENERATOR);
        registry.addRecipeClickArea(GuiCoalGenerator.class, 81, 55, 13, 13, RecipeCategories.COAL_GENERATOR);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.coal_generator), RecipeCategories.COAL_GENERATOR);
        recipeTransfer.addRecipeTransferHandler(ContainerCoalGenerator.class, RecipeCategories.COAL_GENERATOR, 0, 1, 1, 36);
    }

    public static String translateToLocal(String key) {
        if (I18n.canTranslate(key)) return I18n.translateToLocal(key);
        else return I18n.translateToFallback(key);
    }

    public static String translateToLocalFormatted(String key, Object... format) {
        String s = translateToLocal(key);
        try {
            return String.format(s, format);
        } catch (IllegalFormatException e) {
            return "Format error: " + s;
        }
    }
}
