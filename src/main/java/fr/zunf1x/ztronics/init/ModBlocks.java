package fr.zunf1x.ztronics.init;

import fr.zunf1x.ztronics.blocks.BlockBase;
import fr.zunf1x.ztronics.blocks.BlockCoalGenerator;
import fr.zunf1x.ztronics.utils.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = Constants.MODID)
public class ModBlocks {

    public static List<Block> blocks;

    public static Block coal_generator;

    public static void init() {
        blocks = new ArrayList<>();

        coal_generator = new BlockCoalGenerator();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> e) {
        for (Block b : blocks) {
            e.getRegistry().register(b);
        }
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> e) {
        for (Block b : blocks) {
            ItemBlock ib = new ItemBlock(b);
            ib.setRegistryName(Objects.requireNonNull(b.getRegistryName()));

            e.getRegistry().register(ib);
        }
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent e) {
        for (Block b : blocks) {
            registerRender(b, 0);
        }
    }

    public static void registerRender(Block b, int meta) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), meta, new ModelResourceLocation(new ResourceLocation(Constants.MODID, b.getUnlocalizedName().substring(5)),"inventory"));
    }
}
