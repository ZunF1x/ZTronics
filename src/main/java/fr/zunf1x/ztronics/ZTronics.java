package fr.zunf1x.ztronics;

import fr.zunf1x.ztronics.event.GuiHandler;
import fr.zunf1x.ztronics.init.ModBlocks;
import fr.zunf1x.ztronics.machines.coalgenerator.TileEntityCoalGenerator;
import fr.zunf1x.ztronics.utils.Constants;
import fr.zunf1x.ztronics.proxy.CommonProxy;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION)
public class ZTronics {

    @Mod.Instance(Constants.MODID)
    public static ZTronics instance;

    @SidedProxy(clientSide = Constants.CLIENT, serverSide = Constants.SERVER)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit();

        GameRegistry.registerTileEntity(TileEntityCoalGenerator.class, Constants.MODID + ":tile_coal_furnace");

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        ModBlocks.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit();
    }
}
