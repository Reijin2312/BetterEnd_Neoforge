package org.betterx.betterend.interfaces.survives;

import org.betterx.bclib.interfaces.SurvivesOnBlocks;
import org.betterx.betterend.registry.EndBlocks;

import net.minecraft.world.level.block.Block;

import java.util.List;

public interface SurvivesOnShadowGrass extends SurvivesOnBlocks {
    List<Block> BLOCKS = List.of(EndBlocks.SHADOW_GRASS);

    @Override
    default List<Block> getSurvivableBlocks() {
        return BLOCKS;
    }
}
