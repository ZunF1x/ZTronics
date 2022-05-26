package fr.zunf1x.ztronics.blocks;

import fr.zunf1x.ztronics.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBase extends Block {

    public BlockBase(Material material, String name) {
        super(material);

        setRegistryName(name);
        setUnlocalizedName(name);

        ModBlocks.blocks.add(this);
    }

    public BlockBase(Material material, String name, CreativeTabs tab) {
        this(material, name);

        setCreativeTab(tab);
    }

    public BlockBase(Material material, String name, CreativeTabs tab, float hardness, float resistance) {
        this(material, name, tab);

        setHardness(hardness);
        setResistance(resistance);
    }
}
