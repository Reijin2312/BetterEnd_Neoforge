package org.betterx.betterend.blocks.basis;

import org.betterx.bclib.blocks.BaseBlock;
import org.betterx.bclib.interfaces.RuntimeBlockModelProvider;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.jetbrains.annotations.Nullable;

public class LitBaseBlock extends BaseBlock implements RuntimeBlockModelProvider {
    private static final String PATTERN = "{\"parent\":\"betterend:block/cube_noshade\",\"textures\":{\"texture\":\"betterend:block/name\"}}";

    public LitBaseBlock(Properties settings) {
        super(settings);
    }


    @Nullable
    @Override
    @Environment(EnvType.CLIENT)
    public BlockModel getBlockModel(ResourceLocation resourceLocation, BlockState blockState) {
        return BlockModel.fromString(PATTERN.replace("name", resourceLocation.getPath()));
    }
}
