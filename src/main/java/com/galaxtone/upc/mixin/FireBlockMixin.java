/* Copyright (c) 2019 Galaxtone
 * MIT License.
*/

package com.galaxtone.upc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.galaxtone.upc.util.IAreaHelper;

import net.minecraft.block.FireBlock;
import net.minecraft.block.PortalBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;

@Mixin(FireBlock.class)
public class FireBlockMixin {

	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/PortalBlock;createPortalAt(Lnet/minecraft/world/IWorld;Lnet/minecraft/util/math/BlockPos;)Z", ordinal = 0), method = "onBlockAdded")
	public boolean createNormalPortalAt(PortalBlock block, IWorld world, BlockPos pos) {
		IAreaHelper areaHelper = createNormalAreaHelper(world, pos);
		if (areaHelper == null)
			return false;

		areaHelper.createPortal();
		return true;
	}

	private IAreaHelper createNormalAreaHelper(IWorld world, BlockPos pos) {
		IAreaHelper areaHelper = (IAreaHelper) new PortalBlock.AreaHelper(world, pos, Direction.Axis.X);
		if (areaHelper.isValid() && !areaHelper.hasPortalBlocks() && !areaHelper.hasNonObsidianBlocks())
			return areaHelper;

		areaHelper = (IAreaHelper) new PortalBlock.AreaHelper(world, pos, Direction.Axis.Z);
		if (areaHelper.isValid() && !areaHelper.hasPortalBlocks() && !areaHelper.hasNonObsidianBlocks())
			return areaHelper;

		return null;
	}
}
