package org.betterx.betterend.world.features;

import org.betterx.bclib.blocks.BaseAttachedBlock;
import org.betterx.bclib.blocks.BaseWallPlantBlock;
import org.betterx.bclib.util.BlocksHelper;
import org.betterx.betterend.BetterEnd;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/**
 * Places wall/attached plants on vertical surfaces. Uses only local state (no instance field for
 * plant state) so it is safe when the same feature instance is used from multiple threads (e.g. C2ME).
 */
public class WallPlantFeature extends WallScatterFeature<WallPlantFeatureConfig> {

    public WallPlantFeature() {
        super(WallPlantFeatureConfig.CODEC);
    }

    @Override
    public boolean canGenerate(
            WallPlantFeatureConfig cfg,
            WorldGenLevel world,
            RandomSource random,
            BlockPos pos,
            Direction dir
    ) {
        BlockState plant = cfg.getPlantState(random, pos);
        Block block = plant.getBlock();
        if (block instanceof BaseWallPlantBlock) {
            if (!plant.hasProperty(BaseWallPlantBlock.FACING)) {
                BetterEnd.LOGGER.warn(
                        "WallPlantFeature.canGenerate: block {} at {} is BaseWallPlantBlock but has no FACING property; dir={}, state={}",
                        block,
                        pos,
                        dir,
                        plant
                );
                return false;
            }
            BlockState state = plant.setValue(BaseWallPlantBlock.FACING, dir);
            return state.canSurvive(world, pos);
        } else if (block instanceof BaseAttachedBlock) {
            if (!plant.hasProperty(BlockStateProperties.FACING)) {
                BetterEnd.LOGGER.warn(
                        "WallPlantFeature.canGenerate: block {} at {} is BaseAttachedBlock but has no FACING property; dir={}, state={}",
                        block,
                        pos,
                        dir,
                        plant
                );
                return false;
            }
            BlockState state = plant.setValue(BlockStateProperties.FACING, dir);
            return state.canSurvive(world, pos);
        }
        return plant.canSurvive(world, pos);
    }

    @Override
    public void generate(
            WallPlantFeatureConfig cfg,
            WorldGenLevel world,
            RandomSource random,
            BlockPos pos,
            Direction dir
    ) {
        BlockState plant = cfg.getPlantState(random, pos);
        Block block = plant.getBlock();
        if (block instanceof BaseWallPlantBlock) {
            if (!plant.hasProperty(BaseWallPlantBlock.FACING)) {
                BetterEnd.LOGGER.warn(
                        "WallPlantFeature.generate: block {} at {} is BaseWallPlantBlock but has no FACING property; dir={}, state={}",
                        block,
                        pos,
                        dir,
                        plant
                );
                return;
            }
            plant = plant.setValue(BaseWallPlantBlock.FACING, dir);
        } else if (block instanceof BaseAttachedBlock) {
            if (!plant.hasProperty(BlockStateProperties.FACING)) {
                BetterEnd.LOGGER.warn(
                        "WallPlantFeature.generate: block {} at {} is BaseAttachedBlock but has no FACING property; dir={}, state={}",
                        block,
                        pos,
                        dir,
                        plant
                );
                return;
            }
            plant = plant.setValue(BlockStateProperties.FACING, dir);
        }
        BlocksHelper.setWithoutUpdate(world, pos, plant);
    }
}
