package fr.zunf1x.ztronics.machines.coalgenerator;

import fr.zunf1x.ztronics.utils.Constants;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCoalGenerator extends GuiContainer {

    private static final ResourceLocation background = new ResourceLocation(Constants.MODID,"textures/gui/container/coal_generator.png");
    private TileEntityCoalGenerator tile;

    public GuiCoalGenerator(TileEntityCoalGenerator tile, InventoryPlayer inventoryPlayer) {
        super(new ContainerCoalGenerator(tile, inventoryPlayer));
        this.tile = tile;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.mc.getTextureManager().bindTexture(background);
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        if (this.tile.isBurning()) {
            int burningTime = this.tile.getField(0);
            int textureHeight = (int) (13f / this.tile.getFullBurnTime() * burningTime);
            this.drawTexturedModalRect(i + 81, j + 55 + 13 - textureHeight,
                    190, 13 - textureHeight, 13, textureHeight);
        }

        int energy = this.tile.getField(1);
        int capacity = this.tile.getField(2);
        int textureHeight = (int) (42f / capacity * energy);
        this.drawTexturedModalRect(i + 9, j + 22 + 42 - textureHeight, 176, 42 - textureHeight, 14, textureHeight);
    }
}
