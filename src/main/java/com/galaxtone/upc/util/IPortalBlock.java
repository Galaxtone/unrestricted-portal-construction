/* Copyright (c) 2019 Galaxtone
 * MIT License.
*/

package com.galaxtone.upc.util;

import net.minecraft.block.PortalBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public interface IPortalBlock {

	boolean createPortalAt(IWorld world, BlockPos pos);

	boolean createAdvancedPortalAt(IWorld world, BlockPos pos);

	PortalBlock.AreaHelper createAreaHelper(IWorld world, BlockPos pos);

	PortalBlock.AreaHelper createAdvancedAreaHelper(IWorld world, BlockPos pos);
}
