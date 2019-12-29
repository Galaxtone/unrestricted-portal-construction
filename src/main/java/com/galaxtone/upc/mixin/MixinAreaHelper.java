/* Copyright (c) 2019 Galaxtone
 * MIT License.
*/

package com.galaxtone.upc.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.galaxtone.upc.util.IAreaHelper;
import com.galaxtone.upc.util.WorldDelegate;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PortalBlock.AreaHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

@Mixin(AreaHelper.class)
public class MixinAreaHelper implements IAreaHelper {

	@Shadow
	private IWorld world;

	@Shadow
	private int foundPortalBlocks;

	@Shadow
	@Override
	public boolean isValid() {
		return false;
	}

	@Shadow
	@Override
	public void createPortal() {
	}

	private boolean hasNonObsidianBlocks = false;

	@Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/block/PortalBlock$AreaHelper;world:Lnet/minecraft/world/IWorld;", opcode = Opcodes.PUTFIELD, ordinal = 0), method = "<init>")
	public void modifyWorld(AreaHelper areaHelper, IWorld world) {
		this.world = new WorldDelegate(world) {

			@Override
			public BlockState getBlockState(BlockPos pos) {
				BlockState blockState = super.getBlockState(pos);
				if (!blockState.isFullCube(world, pos) || blockState.getBlock() == Blocks.OBSIDIAN)
					return blockState;

				hasNonObsidianBlocks = true;
				return new BlockState(Blocks.OBSIDIAN, null);
			}
		};
	}

	@Override
	public boolean isValidNormally() {
		return this.isValid() && !this.hasNonObsidianBlocks;
	}

	@Override
	public boolean hasPortalBlocks() {
		return this.foundPortalBlocks != 0;
	}
}
