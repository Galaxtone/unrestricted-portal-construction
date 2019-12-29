/* Copyright (c) 2019 Galaxtone
 * MIT License.
*/

package com.galaxtone.upc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.galaxtone.upc.util.IAreaHelper;
import com.galaxtone.upc.util.IPortalBlock;

import net.minecraft.block.PortalBlock;
import net.minecraft.block.PortalBlock.AreaHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;

@Mixin(PortalBlock.class)
public class PortalBlockMixin implements IPortalBlock {

	@Shadow
	@Override
	public boolean createPortalAt(IWorld world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean createAdvancedPortalAt(IWorld world, BlockPos pos) {
		AreaHelper areaHelper = this.createAdvancedAreaHelper(world, pos);
		boolean success = (areaHelper != null);
		if (success)
			areaHelper.createPortal();

		return success;
	}

	@Shadow
	@Override
	public AreaHelper createAreaHelper(IWorld world, BlockPos pos) {
		return null;
	}

	@Override
	public PortalBlock.AreaHelper createAdvancedAreaHelper(IWorld world, BlockPos pos) {
		IAreaHelper areaHelper = (IAreaHelper) new AreaHelper(world, pos, Direction.Axis.X);
		if (areaHelper.isValid() && !areaHelper.hasPortalBlocks())
			return (AreaHelper) areaHelper;

		areaHelper = (IAreaHelper) new PortalBlock.AreaHelper(world, pos, Direction.Axis.Z);
		if (areaHelper.isValid() && !areaHelper.hasPortalBlocks())
			return (AreaHelper) areaHelper;

		return null;
	}

	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/PortalBlock$AreaHelper;isValid()Z"), method = "createAreaHelper")
	public boolean isValidRedirect(AreaHelper areaHelper) {
		return ((IAreaHelper) areaHelper).isValidNormally();
	}
}
