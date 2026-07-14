package org.betterx.betterend.mixin.common;

import org.betterx.betterend.world.generator.GeneratorOptions;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public class LevelMixin {

    @Inject(method = "getSharedSpawnPos", at = @At("HEAD"), cancellable = true)
    private void be_getSharedSpawnPos(CallbackInfoReturnable<BlockPos> info) {
        if (GeneratorOptions.changeSpawn()) {
            if ((Object) this instanceof ServerLevel server) {
                if (server.dimension() == Level.END) {
                    BlockPos pos = GeneratorOptions.getSpawn();
                    info.setReturnValue(pos);
                }
            }
        }
    }
}
