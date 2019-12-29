/* Copyright (c) 2019 Galaxtone
 * MIT License.
*/

package com.galaxtone.upc.item;

import com.galaxtone.upc.ModCompatibility;
import com.galaxtone.upc.util.IPortalBlock;
import com.qouteall.immersive_portals.portal.BreakableMirror;
import com.qouteall.immersive_portals.portal.nether_portal.NewNetherPortalGenerator;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ItemReinforcedIgniter extends Item {
	/*
	 * @Inject(method = "useOnBlock", at = @At("HEAD")) private void
	 * onUseFlintAndSteel( ItemUsageContext context,
	 * CallbackInfoReturnable<ActionResult> cir ) { IWorld world =
	 * context.getWorld(); if (!world.isClient()) { BlockPos blockPos_1 =
	 * context.getBlockPos(); BlockPos firePos =
	 * blockPos_1.offset(context.getSide()); boolean generated =
	 * NewNetherPortalGenerator.onFireLit(((ServerWorld) world), firePos); if
	 * (!generated) { BreakableMirror mirror = BreakableMirror.createMirror(
	 * ((ServerWorld) world), context.getBlockPos(), context.getSide() ); if (mirror
	 * != null) { context.getStack().damage(1, context.getPlayer(), playerEntity_1x
	 * -> playerEntity_1x.sendToolBreakStatus(context.getHand()) ); } } } }
	 */
	public static final DispenserBehavior DISPENSER_BEHAVIOR = new FallibleItemDispenserBehavior() {

		@Override
		protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
			World world = pointer.getWorld();
			BlockPos pos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));

			this.success = createPortalAt(world, pos);
			if (this.success && stack.damage(1, world.random, null))
				stack.setCount(0);

			return stack;
		}
	};

	public ItemReinforcedIgniter() {
		super(new Item.Settings().group(ItemGroup.TOOLS).maxDamage(64));
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (ModCompatibility.isImmersivePresent())
			return useOnBlockImmersive(context);

		World world = context.getWorld();
		BlockPos pos = context.getBlockPos().offset(context.getSide());

		if (world.dimension.getType() != DimensionType.OVERWORLD
				&& world.dimension.getType() != DimensionType.THE_NETHER || !createPortalAt(world, pos))
			return ActionResult.FAIL;

		PlayerEntity player = context.getPlayer();
		world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F,
				RANDOM.nextFloat() * 0.4F + 0.8F);

		ItemStack itemStack = context.getStack();
		if (player instanceof ServerPlayerEntity)
			itemStack.damage(1, player, ((playerEntity) -> {
				playerEntity.sendToolBreakStatus(context.getHand());
			}));

		return ActionResult.SUCCESS;
	}

	private static boolean createPortalAt(World world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		if (!blockState.isAir())
			return false;

		return ((IPortalBlock) Blocks.NETHER_PORTAL).createAdvancedPortalAt(world, pos);
	}

	private ActionResult useOnBlockImmersive(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		Direction facing = context.getSide();
		BlockPos facingPos = pos.offset(facing);

		if (world.isClient)
			return ActionResult.PASS;

		ServerWorld serverWorld = (ServerWorld) world;
		boolean success = NewNetherPortalGenerator.onFireLit(serverWorld, facingPos);
		if (!success)
			success = (BreakableMirror.createMirror(serverWorld, pos, facing) != null);

		PlayerEntity player = context.getPlayer();
		ItemStack itemStack = context.getStack();
		if (success)
			itemStack.damage(1, player, ((playerEntity) -> {
				playerEntity.sendToolBreakStatus(context.getHand());
			}));

		return success ? ActionResult.SUCCESS : ActionResult.FAIL;
	}
}
