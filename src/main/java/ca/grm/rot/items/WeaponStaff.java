package ca.grm.rot.items;

import java.util.List;

import ca.grm.rot.Rot;
import ca.grm.rot.libs.UtilityNBTHelper;
import ca.grm.rot.libs.UtilityWeaponNBTKeyNames;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WeaponStaff extends WeaponCustom {
	public static int	numOfTypes	= 2;
	IIcon[]				blades		= new IIcon[numOfTypes];
	IIcon[]				guards		= new IIcon[numOfTypes];
	IIcon[]				handles		= new IIcon[numOfTypes];
	IIcon				defaultIcon;

	public WeaponStaff() {
		super(ToolMaterial.WOOD);
		setNumberOfColorLayers(3);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		switch (pass) {
			case 0 :
				return this.handles[UtilityNBTHelper.getInt(stack,
						UtilityWeaponNBTKeyNames.handle)];
			case 1 :
				return this.blades[UtilityNBTHelper.getInt(stack,
						UtilityWeaponNBTKeyNames.bladeHead)];
			case 2 :
				return this.guards[UtilityNBTHelper.getInt(stack,
						UtilityWeaponNBTKeyNames.guard)];
			default :
				break;
		}
		return this.defaultIcon;
	}
	
	public IIcon[] getIcons(ItemStack stack) {
		return new IIcon[]{
				this.handles[UtilityNBTHelper.getInt(stack,
						UtilityWeaponNBTKeyNames.handle)],
				this.blades[UtilityNBTHelper.getInt(stack,
						UtilityWeaponNBTKeyNames.bladeHead)],
				this.guards[UtilityNBTHelper
						.getInt(stack, UtilityWeaponNBTKeyNames.guard)]};
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
		ItemStack[] staves = new ItemStack[numOfTypes];
		for (int i = 0; i < numOfTypes; i++) {
			staves[i] = new ItemStack(p_150895_1_, 1, 0);
			UtilityNBTHelper.setString(staves[i], UtilityWeaponNBTKeyNames.type, "staff");
			UtilityNBTHelper.setInteger(staves[i], UtilityWeaponNBTKeyNames.handle, i);
			UtilityNBTHelper.setInteger(staves[i], UtilityWeaponNBTKeyNames.bladeHead, i);
			UtilityNBTHelper.setInteger(staves[i], UtilityWeaponNBTKeyNames.guard, i);
			p_150895_3_.add(staves[i]);
		}
	}

	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		UtilityNBTHelper.setString(par1ItemStack, UtilityWeaponNBTKeyNames.type, "staff");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {

		EntityArrow a = new EntityArrow(par2World, par3EntityPlayer, 1.0f);
		EntityFireball fb = new EntityFireball(par2World, a.posX, a.posY, a.posZ,
				a.motionX, a.motionY, a.motionZ) {

			@Override
			protected void onImpact(MovingObjectPosition var1) {
				if (var1.entityHit != this.shootingEntity) {
					this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ,
							2.5f, false);
					this.setDead();
				}
			}
		};
		fb.shootingEntity = par3EntityPlayer;
		if (!par2World.isRemote) {
			par2World.spawnEntityInWorld(fb);
		}
		
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		for (int i = 0; i < numOfTypes; i++) {
			this.blades[i] = ir.registerIcon(Rot.MODID + ":" + "weapons/blades/staff_"
					+ i);
			this.guards[i] = ir.registerIcon(Rot.MODID + ":" + "weapons/guards/cradle_"
					+ i);
			this.handles[i] = ir.registerIcon(Rot.MODID + ":" + "weapons/handles/rod_"
					+ i);
		}
		this.defaultIcon = ir
				.registerIcon(Rot.MODID + ":" + "weapons/fighter_slash_icon");
	}
}