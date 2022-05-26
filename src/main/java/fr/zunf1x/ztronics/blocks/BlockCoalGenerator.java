package fr.zunf1x.ztronics.blocks;

import fr.zunf1x.ztronics.ZTronics;
import fr.zunf1x.ztronics.init.ModBlocks;
import fr.zunf1x.ztronics.machines.coalgenerator.TileEntityCoalGenerator;
import fr.zunf1x.ztronics.utils.Constants;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCoalGenerator extends BlockContainer {

    public BlockCoalGenerator() {
        super(Material.IRON);

        setRegistryName("coal_generator");
        setUnlocalizedName("coal_generator");
        setCreativeTab(CreativeTabs.DECORATIONS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityCoalGenerator();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity te = world.getTileEntity(pos);

        if (te instanceof TileEntityCoalGenerator) {
            InventoryHelper.dropInventoryItems(world, pos,
                    (TileEntityCoalGenerator) te);
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(pos);

            if (te instanceof TileEntityCoalGenerator)
                player.openGui(ZTronics.instance, Constants.COAL_GENERATOR, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity te = worldIn.getTileEntity(pos);

            if (te instanceof TileEntityCoalGenerator)
                ((TileEntityCoalGenerator) te).setCustomName(stack.getDisplayName());
        }
    }
}
