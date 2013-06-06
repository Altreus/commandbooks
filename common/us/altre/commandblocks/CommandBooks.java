package us.altre.commandblocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import us.altre.commandblocks.item.ItemCommandBook;
import us.altre.commandblocks.lib.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod( modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.VERSION)
public class CommandBooks {

	public static Item commandBook;
	@PreInit
	public void preInit(FMLPreInitializationEvent e) {
		// FIXME: config
		commandBook = new ItemCommandBook(19000);
		commandBook.setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Init
	public void init(FMLInitializationEvent e) {
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent e) {
		
	}
}
