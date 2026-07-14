package org.betterx.betterend.mixin.client;

import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.world.generator.GeneratorOptions;

import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BlockStateModelLoader.class)
public abstract class BlockStateModelLoaderMixin {
    @ModifyArg(method = "loadBlockStateDefinitions", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/FileToIdConverter;idToFile(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/resources/ResourceLocation;"))
    public ResourceLocation be_switchModelOnLoad(ResourceLocation loc) {
        //this should allways be a block state, as it is supplied a BLOCKSTATE_LISTER
        if (GeneratorOptions.changeChorusPlant() && be_changeModel(loc)) {
            String path = loc.getPath().replace("chorus", "custom_chorus");
            return BetterEnd.C.mk(path);
        }
        return loc;
    }

    @Unique
    private boolean be_changeModel(ResourceLocation id) {
        if (id.getNamespace().equals("minecraft")) {
            if (id.getPath().equals("chorus_plant") || id.getPath().equals("chorus_flower")) {
                return true;
            }
            return false;
        }
        return false;
    }
}
