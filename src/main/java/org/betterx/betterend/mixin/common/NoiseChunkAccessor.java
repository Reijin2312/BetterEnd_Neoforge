package org.betterx.betterend.mixin.common;

import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseSettings;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(NoiseChunk.class)
public abstract class NoiseChunkAccessor {
    public NoiseSettings bnv_getNoiseSettings() {
        return null;
    }

    public int bnv_getCellCountXZ() {
        return 0;
    }

    public int bnv_getCellCountY() {
        return 0;
    }

    public int bnv_getFirstCellZ() {
        return 0;
    }

    public int bnv_getCellNoiseMinY() {
        return 0;
    }
}
