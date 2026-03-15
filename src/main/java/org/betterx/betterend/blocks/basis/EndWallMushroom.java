package org.betterx.betterend.blocks.basis;

import org.betterx.bclib.blocks.WallMushroomBlock;
import org.betterx.betterend.interfaces.survives.SurvivesOnEndStone;
import org.betterx.wover.block.api.model.BlockModelProvider;
import org.betterx.wover.block.api.model.WoverBlockModelGenerators;

import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


public class EndWallMushroom extends WallMushroomBlock implements SurvivesOnEndStone, BlockModelProvider {

    public EndWallMushroom(int light) {
        super(light);
    }

    @Override
    public boolean isTerrain(BlockState state) {
        return SurvivesOnEndStone.super.isTerrain(state);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void provideBlockModels(Object modelGenerator) {
    WoverBlockModelGenerators generator = (WoverBlockModelGenerators) modelGenerator;
        generator.createCubeModel(this);
        generator.createFlatItem(this);
    }
}
