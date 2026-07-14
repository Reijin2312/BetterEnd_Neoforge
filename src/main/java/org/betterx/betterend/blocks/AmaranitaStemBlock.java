package org.betterx.betterend.blocks;

import org.betterx.bclib.blocks.BaseRotatedPillarBlock;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class AmaranitaStemBlock extends BaseRotatedPillarBlock {
    public AmaranitaStemBlock() {
        super(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_LIGHT_GREEN));
    }
}
