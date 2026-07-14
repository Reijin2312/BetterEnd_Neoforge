package org.betterx.betterend.blocks;

import org.betterx.bclib.behaviours.interfaces.BehaviourStone;
import org.betterx.bclib.blocks.BaseBlock;
import org.betterx.bclib.interfaces.Fuel;

import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class CharcoalBlock extends BaseBlock implements Fuel, BehaviourStone {
    public CharcoalBlock() {
        super(FabricBlockSettings.copyOf(Blocks.COAL_BLOCK));
    }

    @Override
    public int getFuelTime() {
        return 16000;
    }
}
