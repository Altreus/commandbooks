package us.altre.commandblocks.item;

import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.World;

public class ItemCommandBook extends ItemEditableBook implements ICommandSender {

	protected String command;
	protected String commandSenderName = "@";
	protected EntityPlayer usingPlayer;
	
	public ItemCommandBook(int id) {
		super(id);
	}

	public void setCommandSenderName(String str) {
		commandSenderName = str;
	}
	
	@Override
	public String getCommandSenderName() {
		// TODO Auto-generated method stub
		return commandSenderName;
	}

	@Override
	public void sendChatToPlayer(String s) {}

	@Override
	public boolean canCommandSenderUseCommand(int i, String s) {
		// I don't know what the parameters are, but this is what the command block does
		return i <= 2;
	}

	@Override
	public String translateString(String s, Object... var2) {
		// I don't know what the parameters are, but this is what the command block does
		return s;
	}

	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		FMLLog.log(Level.INFO, "%s", "Get player location");
		if (usingPlayer == null) {
			return new ChunkCoordinates(0,0,0);
		}
		
		ChunkCoordinates c = usingPlayer.getPlayerCoordinates();
		FMLLog.log(Level.INFO, "Player: %d, %d, %d", c.posX, c.posY, c.posZ);
		
		return usingPlayer.getPlayerCoordinates();
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		// TODO: Don't do this in creative mode; this is when the user wants to actually write the book
		
		runCommand(world, player);
		
		player.displayGUIBook(itemStack);
		return itemStack;
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, 
			World world, int x, int y, int z, int sideHit, 
			float hitVecX, float hitVecY, float hitVecZ) {
		
		// If we right-click on a command block, yoink the command.
		// Currently this only gets called with shift+right-click
		if (world.getBlockId(x, y, z) == Block.commandBlock.blockID) {
			TileEntityCommandBlock c = (TileEntityCommandBlock) world.getBlockTileEntity(x, y, z);
			command = c.getCommand();
			commandSenderName = c.getCommandSenderName();
			FMLLog.log(Level.INFO, "Set command: %s", command);
			FMLLog.log(Level.INFO, "Set command sender: %s", commandSenderName);
			
			return true;
		}

		return super.onItemUse(itemStack, entityPlayer, world, x, y, z, sideHit, hitVecX, hitVecY, hitVecZ);
	}

	private void runCommand(World world, EntityPlayer player) {
		// If we're on the server, run the command. Not sure what to do about the return value of executeCommand
        if (! world.isRemote && this.command != null)
        {
            MinecraftServer minecraftserver = MinecraftServer.getServer();

            if (minecraftserver != null && minecraftserver.isCommandBlockEnabled())
            {
    			FMLLog.log(Level.INFO, "Run command: %s", this.command);
    			usingPlayer = player;
    			commandSenderName = player.username;
                ICommandManager icommandmanager = minecraftserver.getCommandManager();
                int r = icommandmanager.executeCommand(this, this.command);
                FMLLog.log(Level.INFO, "%d", r);
            }
        
        }
	}
}