package org.betterx.betterend.blocks;

import org.betterx.bclib.behaviours.interfaces.BehaviourStone;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.blocks.basis.PedestalBlock;
import org.betterx.betterend.client.models.EndModels;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class PedestalVanilla extends PedestalBlock implements BehaviourStone {

    public PedestalVanilla(Block parent) {
        super(parent);
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected TextureMapping createTextureMapping() {
        final var parentTexture = BuiltInRegistries.BLOCK.getKey(parent);
        final var name = parentTexture.getPath().replace("_block", "");
        final var baseTextureLocation = ResourceLocation.fromNamespaceAndPath(parentTexture.getNamespace(), name);
        final var polishedTexture = baseTextureLocation.withPrefix("block/polished_");
        final var pillarTexture = BetterEnd.C.convertNamespace(baseTextureLocation
                .withPrefix("block/")
                .withSuffix("_pillar"));

        return new TextureMapping()
                .put(TextureSlot.TOP, polishedTexture)
                .put(TextureSlot.BOTTOM, polishedTexture)
                .put(EndModels.BASE, polishedTexture)
                .put(EndModels.PILLAR, pillarTexture);
    }
}
