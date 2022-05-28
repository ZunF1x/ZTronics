package fr.zunf1x.ztronics.plugins.jei.coalgenerator;

import com.google.common.collect.Lists;
import mezz.jei.api.IJeiHelpers;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CoalGeneratorRecipeMaker {

    public static List<CoalGeneratorRecipe> getRecipes(IJeiHelpers helpers) {
        List<CoalGeneratorRecipe> jeiRecipes = Lists.newArrayList();

        ItemStack input = new ItemStack(Items.COAL);
        CoalGeneratorRecipe recipe = new CoalGeneratorRecipe(input);
        jeiRecipes.add(recipe);

        return jeiRecipes;
    }
}
