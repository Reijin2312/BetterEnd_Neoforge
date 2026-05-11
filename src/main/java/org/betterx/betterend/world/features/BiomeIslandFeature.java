package org.betterx.betterend.world.features;

import org.betterx.bclib.api.v2.levelgen.features.features.DefaultFeature;
import org.betterx.bclib.sdf.SDF;
import org.betterx.bclib.sdf.operator.SDFDisplacement;
import org.betterx.bclib.sdf.operator.SDFTranslate;
import org.betterx.bclib.sdf.primitive.SDFCappedCone;
import org.betterx.bclib.util.BlocksHelper;
import org.betterx.betterend.noise.OpenSimplexNoise;
import org.betterx.betterend.world.biome.EndBiome;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BiomeIslandFeature extends DefaultFeature {
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featureConfig) {
        final BlockPos pos = featureConfig.origin();
        final WorldGenLevel world = featureConfig.level();
        //Holder<Biome> biome = world.getBiome(pos);
        int dist = BlocksHelper.downRay(world, pos, 10) + 1;
        BlockPos surfacePos = new BlockPos(pos.getX(), pos.getY() - dist, pos.getZ());
        BlockState topMaterial = EndBiome.findTopMaterial(world, surfacePos);

        BlockState topBlock;
        BlockState underBlock;
        if (BlocksHelper.isFluid(topMaterial)) {
            topBlock = Blocks.GRAVEL.defaultBlockState();
            underBlock = Blocks.STONE.defaultBlockState();
        } else {
            topBlock = topMaterial;
            underBlock = EndBiome.findUnderMaterial(world, surfacePos);
        }

        createSDFIsland(pos, new OpenSimplexNoise(world.getSeed()), topBlock, underBlock).fillRecursive(world, pos.below());
        return true;
    }

    private static SDF createSDFIsland(
            BlockPos center,
            OpenSimplexNoise simplexNoise,
            BlockState topBlock,
            BlockState underBlock
    ) {
        SDF sdfCone = new SDFCappedCone().setRadius1(0).setRadius2(6).setHeight(4).setBlock(pos -> {
            if (pos.getY() > center.getY()) return AIR;
            if (pos.getY() == center.getY()) return topBlock;
            return underBlock;
        });
        sdfCone = new SDFTranslate().setTranslate(0, -2, 0).setSource(sdfCone);
        sdfCone = new SDFDisplacement().setFunction(pos -> {
                                           float deltaX = Math.abs(pos.x());
                                           float deltaY = Math.abs(pos.y());
                                           float deltaZ = Math.abs(pos.z());
                                           if (deltaY < 2.0f && (deltaX < 3.0f || deltaZ < 3.0F)) return 0.0f;
                                           return (float) simplexNoise.eval(center.getX() + pos.x(), center.getY() + pos.y(), center.getZ() + pos.z());
                                       })
                                       .setSource(sdfCone)
                                       .setReplaceFunction(state -> BlocksHelper.isFluid(state) || state.canBeReplaced());
        return sdfCone;
    }
}
