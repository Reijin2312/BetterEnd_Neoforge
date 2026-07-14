package org.betterx.betterend.interfaces;

import net.minecraft.world.level.block.Block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface PottablePlant {
    boolean canPlantOn(Block block);

    default boolean canBePotted() {
        return true;
    }

    @Environment(EnvType.CLIENT)
    default String getPottedState() {
        return "";
    }
}
