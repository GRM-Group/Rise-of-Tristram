package ee.rot.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.Rot;
import ee.rot.comms.TextPacket;
import ee.rot.libs.ExtendPlayer;

public class ItemManaConverter extends Item {

	private int delayTime = 60;
	private int coolDown = 0;
	
	public ItemManaConverter() {
		super();
		
		setMaxStackSize(1);
	}
		
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) 
	{
		ExtendPlayer props = ExtendPlayer.get(par3EntityPlayer);
		if (par3EntityPlayer.inventory.getFirstEmptyStack() > 0)
		{
			if (props.consumeMana(60f))
			{
				par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(RotItems.itemMana,1,1));
			}
		}
		
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		// TODO Auto-generated method stub
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		//par3List.add("");
	}
}
