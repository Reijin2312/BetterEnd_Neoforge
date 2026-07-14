package org.betterx.betterend.blocks;

import org.betterx.bclib.blocks.BaseAttachedBlock;
import org.betterx.bclib.client.render.BCLRenderLayer;
import org.betterx.bclib.interfaces.RenderLayerProvider;
import org.betterx.betterend.client.models.EndModels;
import org.betterx.wover.block.api.model.BlockModelProvider;
import org.betterx.wover.block.api.model.WoverBlockModelGenerators;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import com.google.common.collect.Maps;

import java.util.EnumMap;

public class ChandelierBlock extends BaseAttachedBlock.Metal implements RenderLayerProvider, BlockModelProvider {
    private static final EnumMap<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(Direction.class);

    public ChandelierBlock(Block source) {
        super(FabricBlockSettings.copyOf(source)
                                 .lightLevel((bs) -> 15)
                                 .noCollission()
                                 .noOcclusion()
                                 .requiresCorrectToolForDrops());
    }

    @Override
    public BCLRenderLayer getRenderLayer() {
        return BCLRenderLayer.CUTOUT;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        return BOUNDING_SHAPES.get(state.getValue(FACING));
    }

    @Override
    public void provideBlockModels(WoverBlockModelGenerators generator) {
        final var baseTexture = TextureMapping.getBlockTexture(this);
        final var mapping = new TextureMapping()
                .put(EndModels.WALL, baseTexture.withSuffix("_wall"))
                .put(EndModels.FLOOR, baseTexture.withSuffix("_floor"))
                .put(EndModels.CEIL, baseTexture.withSuffix("_ceil"));

        final var modelCeil = EndModels.CHANDELIER_CEIL.createWithSuffix(this, "_ceil", mapping, generator.modelOutput());
        final var modelWall = EndModels.CHANDELIER_WALL.createWithSuffix(this, "_wall", mapping, generator.modelOutput());
        final var modelFloor = EndModels.CHANDELIER_FLOOR.createWithSuffix(this, "_floor", mapping, generator.modelOutput());

        final var prop = PropertyDispatch.property(FACING);
        prop.select(Direction.DOWN, Variant.variant().with(VariantProperties.MODEL, modelCeil));
        prop.select(Direction.UP, Variant.variant().with(VariantProperties.MODEL, modelFloor));
        prop.select(Direction.EAST, Variant
                .variant()
                .with(VariantProperties.MODEL, modelWall)
                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270));
        prop.select(Direction.SOUTH, Variant.variant().with(VariantProperties.MODEL, modelWall));
        prop.select(Direction.WEST, Variant
                .variant()
                .with(VariantProperties.MODEL, modelWall)
                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90));
        prop.select(Direction.NORTH, Variant
                .variant()
                .with(VariantProperties.MODEL, modelWall)
                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180));

        generator.acceptBlockState(MultiVariantGenerator.multiVariant(this).with(prop));
        generator.delegateItemModel(this, modelCeil);
    }

    static {
        BOUNDING_SHAPES.put(Direction.UP, Block.box(5, 0, 5, 11, 13, 11));
        BOUNDING_SHAPES.put(Direction.DOWN, Block.box(5, 3, 5, 11, 16, 11));
        BOUNDING_SHAPES.put(Direction.NORTH, Shapes.box(0.0, 0.0, 0.5, 1.0, 1.0, 1.0));
        BOUNDING_SHAPES.put(Direction.SOUTH, Shapes.box(0.0, 0.0, 0.0, 1.0, 1.0, 0.5));
        BOUNDING_SHAPES.put(Direction.WEST, Shapes.box(0.5, 0.0, 0.0, 1.0, 1.0, 1.0));
        BOUNDING_SHAPES.put(Direction.EAST, Shapes.box(0.0, 0.0, 0.0, 0.5, 1.0, 1.0));
    }
}
