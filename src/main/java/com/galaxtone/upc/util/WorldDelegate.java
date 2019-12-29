/* Copyright (c) 2019 Galaxtone
 * MIT License.
*/

package com.galaxtone.upc.util;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.IWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.TickScheduler;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.level.LevelProperties;

public class WorldDelegate implements IWorld {

	private final IWorld world;

	public WorldDelegate(IWorld world) {
		this.world = world;
	}

	@Override
	public List<Entity> getEntities(Entity except, Box box,
			Predicate<? super Entity> predicate) {
		return this.world.getEntities(except, box, predicate);
	}

	@Override
	public <T extends Entity> List<T> getEntities(
			Class<? extends T> entityClass, Box box,
			Predicate<? super T> predicate) {
		return this.world.getEntities(entityClass, box, predicate);
	}

	@Override
	public List<? extends PlayerEntity> getPlayers() {
		return this.world.getPlayers();
	}

	@Override
	public Chunk getChunk(int chunkX, int chunkZ, ChunkStatus leastStatus,
			boolean create) {
		return this.world.getChunk(chunkX, chunkZ, leastStatus, create);
	}

	@Override
	public int getTopY(Type heightmap, int x, int z) {
		return this.world.getTopY(heightmap, x, z);
	}

	@Override
	public int getAmbientDarkness() {
		return this.world.getAmbientDarkness();
	}

	@Override
	public BiomeAccess getBiomeAccess() {
		return this.world.getBiomeAccess();
	}

	@Override
	public Biome getGeneratorStoredBiome(int biomeX, int biomeY, int biomeZ) {
		return this.world.getGeneratorStoredBiome(biomeX, biomeY, biomeZ);
	}

	@Override
	public boolean isClient() {
		return this.world.isClient();
	}

	@Override
	public int getSeaLevel() {
		return this.world.getSeaLevel();
	}

	@Override
	public Dimension getDimension() {
		return this.world.getDimension();
	}

	@Override
	public LightingProvider getLightingProvider() {
		return this.world.getLightingProvider();
	}

	@Override
	public BlockEntity getBlockEntity(BlockPos pos) {
		return this.world.getBlockEntity(pos);
	}

	@Override
	public BlockState getBlockState(BlockPos pos) {
		return this.world.getBlockState(pos);
	}

	@Override
	public FluidState getFluidState(BlockPos pos) {
		return this.world.getFluidState(pos);
	}

	@Override
	public WorldBorder getWorldBorder() {
		return this.world.getWorldBorder();
	}

	@Override
	public boolean testBlockState(BlockPos blockPos,
			Predicate<BlockState> state) {
		return this.world.testBlockState(blockPos, state);
	}

	@Override
	public boolean setBlockState(BlockPos pos, BlockState state, int flags) {
		return this.world.setBlockState(pos, state, flags);
	}

	@Override
	public boolean removeBlock(BlockPos pos, boolean move) {
		return this.world.removeBlock(pos, move);
	}

	@Override
	public boolean breakBlock(BlockPos pos, boolean drop,
			Entity breakingEntity) {
		return this.world.breakBlock(pos, drop, breakingEntity);
	}

	@Override
	public long getSeed() {
		return this.world.getSeed();
	}

	@Override
	public TickScheduler<Block> getBlockTickScheduler() {
		return this.world.getBlockTickScheduler();
	}

	@Override
	public TickScheduler<Fluid> getFluidTickScheduler() {
		return this.world.getFluidTickScheduler();
	}

	@Override
	public World getWorld() {
		return this.world.getWorld();
	}

	@Override
	public LevelProperties getLevelProperties() {
		return this.world.getLevelProperties();
	}

	@Override
	public LocalDifficulty getLocalDifficulty(BlockPos pos) {
		return this.world.getLocalDifficulty(pos);
	}

	@Override
	public ChunkManager getChunkManager() {
		return this.world.getChunkManager();
	}

	@Override
	public Random getRandom() {
		return this.world.getRandom();
	}

	@Override
	public void updateNeighbors(BlockPos pos, Block block) {
		this.world.updateNeighbors(pos, block);
	}

	@Override
	public BlockPos getSpawnPos() {
		return this.world.getSpawnPos();
	}

	@Override
	public void playSound(PlayerEntity player, BlockPos blockPos,
			SoundEvent soundEvent, SoundCategory soundCategory, float volume,
			float pitch) {
		this.world.playSound(player, blockPos, soundEvent, soundCategory,
				volume, pitch);
	}

	@Override
	public void addParticle(ParticleEffect parameters, double x, double y,
			double z, double velocityX, double velocityY, double velocityZ) {
		this.world.addParticle(parameters, x, y, z, velocityX, velocityY,
				velocityZ);
	}

	@Override
	public void playLevelEvent(PlayerEntity player, int eventId,
			BlockPos blockPos, int data) {
		this.world.playLevelEvent(player, eventId, blockPos, data);
	}
}
