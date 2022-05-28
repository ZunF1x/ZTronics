package fr.zunf1x.ztronics.plugins.jei.coalgenerator;

import fr.zunf1x.ztronics.utils.Constants;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractCoalGeneratorRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

    protected static final ResourceLocation TEXTURES = new ResourceLocation(Constants.MODID, "textures/gui/container/coal_generator.png");

    protected static final int fuel = 0;

    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;
    protected final IDrawableAnimated animatedEnergy;

    public AbstractCoalGeneratorRecipeCategory(IGuiHelper helper) {
        staticFlame = helper.createDrawable(TEXTURES, 190, 0, 13, 13);
        animatedFlame = helper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);

        IDrawableStatic staticEnergy = helper.createDrawable(TEXTURES, 176, 0, 14, 42);
        animatedEnergy = helper.createAnimatedDrawable(staticEnergy, 200, IDrawableAnimated.StartDirection.BOTTOM, false);
    }
}
