package ee.rot.comms;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import ee.rot.ExtendPlayerRotManaStam;
import ee.rot.RotOld;
import ee.rot.comms.customPacket.customPacketHandler;

public class CommonProxy implements IGuiHandler
{
	/** Used to store IExtendedEntityProperties data temporarily between player death and respawn */
	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();
	
	public void registerRenderers() 
	{
		
	}	
	
	public void registerMessages() 
	{
		RotOld.net.registerMessage(customPacketHandler.class, customPacket.class, 0, Side.SERVER);
	}
	
	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
	
	/**
	* Adds an entity's custom data to the map for temporary storage
	* @param compound An NBT Tag Compound that stores the IExtendedEntityProperties data only
	*/
	public static void storeEntityData(String name, NBTTagCompound compound)
	{
		extendedEntityData.put(name, compound);
	}
	
	/**
	* Removes the compound from the map and returns the NBT tag stored for name or null if none exists
	*/
	public static NBTTagCompound getEntityData(String name)
	{
		return extendedEntityData.remove(name);
	}
	
	/**
	* Makes it look nicer in the methods save/loadProxyData
	*/
	private static String getSaveKey(EntityPlayer player) 
	{
		return player.getCommandSenderName() + ":" + ExtendPlayerRotManaStam.EXT_PROP_NAME;
	}
	
	/**
	* Does everything I did in onLivingDeathEvent and it's static,
	* so you now only need to use the following in the above event:
	* ExtendedPlayer.saveProxyData((EntityPlayer) event.entity));
	*/
	public static void saveProxyData(EntityPlayer player) 
	{
		ExtendPlayerRotManaStam playerData = ExtendPlayerRotManaStam.get(player);
		NBTTagCompound savedData = new NBTTagCompound();
		
		playerData.saveNBTData(savedData);
		// Note that we made the CommonProxy method storeEntityData static,
		// so now we don't need an instance of CommonProxy to use it! Great!
		CommonProxy.storeEntityData(getSaveKey(player), savedData);
	}
	
	/**
	* This cleans up the onEntityJoinWorld event by replacing most of the code
	* with a single line: ExtendedPlayer.loadProxyData((EntityPlayer) event.entity));
	*/
	public static void loadProxyData(EntityPlayer player) 
	{
		ExtendPlayerRotManaStam playerData = ExtendPlayerRotManaStam.get(player);
		NBTTagCompound savedData = CommonProxy.getEntityData(getSaveKey(player));
		
		if(savedData != null) 
		{
			playerData.loadNBTData(savedData);
		}
		// note we renamed 'syncExtendedProperties' to 'syncProperties' because yay, it's shorter
		//playerData.;
	}
}
