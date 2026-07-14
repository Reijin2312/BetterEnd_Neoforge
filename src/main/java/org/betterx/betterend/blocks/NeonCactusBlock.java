package org.betterx.betterend.blocks;

import org.betterx.betterend.blocks.basis.LitPillarBlock;

import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class NeonCactusBlock extends LitPillarBlock {
    public NeonCactusBlock() {
        super(FabricBlockSettings.copyOf(Blocks.CACTUS).lightLevel((bs) -> 15));
    }
}
