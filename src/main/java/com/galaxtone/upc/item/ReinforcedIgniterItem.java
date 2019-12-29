/* Copyright (c) 2019 Galaxtone
 * MIT License.
*/

package com.galaxtone.upc.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PortalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ReinforcedIgniterItem extends FlintAndSteelItem {

	public ReinforcedIgniterItem() {
		super(new Item.Settings().group(ItemGroup.TOOLS).maxDamage(24));
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos().offset(context.getSide());

		BlockState blockState = world.getBlockState(pos);
		if (!blockState.isAir())
			return ActionResult.FAIL;

		PortalBlock.AreaHelper areaHelper = ((PortalBlock) Blocks.NETHER_PORTAL).createAreaHelper(world, pos);
		if (areaHelper == null)
			return ActionResult.FAIL;

		PlayerEntity player = context.getPlayer();
		world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F,
				RANDOM.nextFloat() * 0.4F + 0.8F);

		if (world.dimension.getType() == DimensionType.OVERWORLD
				|| world.dimension.getType() == DimensionType.THE_NETHER)
			areaHelper.createPortal();

		ItemStack itemStack = context.getStack();
		if (player instanceof ServerPlayerEntity) {
			itemStack.damage(1, player, ((playerEntity) -> {
				playerEntity.sendToolBreakStatus(context.getHand());
			}));
		}

		return ActionResult.SUCCESS;
	}
}
