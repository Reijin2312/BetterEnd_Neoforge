package org.betterx.betterend.blocks;

import org.betterx.bclib.behaviours.interfaces.BehaviourStone;
import org.betterx.bclib.blocks.BaseBlock;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class AmberBlock extends BaseBlock implements BehaviourStone {
    public AmberBlock() {
        super(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_YELLOW));
    }
}
