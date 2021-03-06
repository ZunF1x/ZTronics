package fr.zunf1x.ztronics.event;

import fr.zunf1x.ztronics.machines.coalgenerator.ContainerCoalGenerator;
import fr.zunf1x.ztronics.machines.coalgenerator.GuiCoalGenerator;
import fr.zunf1x.ztronics.machines.coalgenerator.TileEntityCoalGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID) {
            case 0:
                return new ContainerCoalGenerator((TileEntityCoalGenerator) te, player.inventory);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID) {
            case 0:
                return new GuiCoalGenerator((TileEntityCoalGenerator) te, player.inventory);
        }

        return null;
    }
}