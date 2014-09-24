package ee.rot.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.rot.Rot;
import ee.rot.libs.UtilityNBTHelper;
import ee.rot.libs.UtilityWeaponNBTKeyNames;

public class WeaponSlash extends WeaponCustom
{
	public static int numOfTypes = 7;
	IIcon[] blades = new IIcon[numOfTypes];
	IIcon[] guards = new IIcon[numOfTypes];
	IIcon[] handles = new IIcon[numOfTypes];
	IIcon defaultIcon;
	public WeaponSlash(ToolMaterial mat) 
	{
		super(mat);
	}
	
	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer)
	{
		UtilityNBTHelper.setString(par1ItemStack, UtilityWeaponNBTKeyNames.type, "slash");
		UtilityNBTHelper.setString(par1ItemStack, UtilityWeaponNBTKeyNames.size, "normal");
		UtilityNBTHelper.setInteger(par1ItemStack, UtilityWeaponNBTKeyNames.bladeHead, 3);
		UtilityNBTHelper.setInteger(par1ItemStack, UtilityWeaponNBTKeyNames.guard, 3);
		UtilityNBTHelper.setInteger(par1ItemStack, UtilityWeaponNBTKeyNames.handle, 3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		for (int i = 0; i < numOfTypes; i++)
		{
			blades[i] = ir.registerIcon(Rot.MODID+":"+"weapons/blades/blade_"+i);
			guards[i] = ir.registerIcon(Rot.MODID+":"+"weapons/guards/guard_"+i);
			handles[i] = ir.registerIcon(Rot.MODID+":"+"weapons/handles/handle_"+i);
		}
		defaultIcon = ir.registerIcon(Rot.MODID+":"+"weapons/fighter_slash_icon");
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		switch(pass)
		{
			case 0: 
				return handles[UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.handle)];
			case 1:
				return blades[UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.bladeHead)];
			case 2:
				return guards[UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.guard)];
			default:
				break;
		}
		return defaultIcon;
	}
	
	public IIcon[] getIcons(ItemStack stack)
	{
		return new IIcon[]{
				handles[UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.handle)],
				blades[UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.bladeHead)],
				guards[UtilityNBTHelper.getInt(stack, UtilityWeaponNBTKeyNames.guard)]};
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_,
			List p_150895_3_)
	{
		ItemStack[] swords = new ItemStack[numOfTypes];
		for (int i = 0; i < numOfTypes; i++)
		{
			swords[i] = new ItemStack(p_150895_1_,1,0);
			//UtilityNBTHelper.setInteger(swords[i], UtilityWeaponNBTKeyNames.layerColor+1, 0xffffbb);
			UtilityNBTHelper.setString(swords[i], UtilityWeaponNBTKeyNames.type, "slash");
			UtilityNBTHelper.setString(swords[i], UtilityWeaponNBTKeyNames.size, "normal");
			UtilityNBTHelper.setInteger(swords[i], UtilityWeaponNBTKeyNames.handle, i);
			UtilityNBTHelper.setInteger(swords[i], UtilityWeaponNBTKeyNames.bladeHead, i);
			UtilityNBTHelper.setInteger(swords[i], UtilityWeaponNBTKeyNames.guard, i);
			p_150895_3_.add(swords[i]);
		}
	}

}
