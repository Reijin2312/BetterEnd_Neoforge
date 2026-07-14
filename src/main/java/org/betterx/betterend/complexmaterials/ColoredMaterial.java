package org.betterx.betterend.complexmaterials;

import org.betterx.bclib.util.BlocksHelper;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.registry.EndBlocks;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.recipe.api.RecipeBuilder;
import org.betterx.wover.tag.api.event.context.ItemTagBootstrapContext;
import org.betterx.wover.tag.api.event.context.TagBootstrapContext;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ColoredMaterial implements MaterialManager.Material {
    private static final Map<Integer, ItemLike> DYES = Maps.newHashMap();
    private static final Map<Integer, String> COLORS = Maps.newHashMap();
    private final Map<Integer, Block> colors = Maps.newHashMap();

    public ColoredMaterial(Function<BlockBehaviour.Properties, Block> constructor, Block source, boolean craftEight) {
        this(constructor, source, COLORS, DYES, craftEight);
    }

    private List<MaterialManager.MaterialRecipe> RECIPES;

    public ColoredMaterial(
            Function<BlockBehaviour.Properties, Block> constructor,
            Block source,
            Map<Integer, String> colors,
            Map<Integer, ItemLike> dyes,
            boolean craftEight
    ) {
        if (ModCore.isDatagen()) {
            RECIPES = new ArrayList<>(colors.size());
            MaterialManager.register(this);
        }
        String id = BuiltInRegistries.BLOCK.getKey(source).getPath();
        colors.forEach((color, name) -> {
            String blockName = id + "_" + name;
            Block block = constructor.apply(BlockBehaviour.Properties
                    .ofFullCopy(source)
                    .mapColor(MapColor.COLOR_BLACK));
            EndBlocks.registerBlock(blockName, block);
            if (ModCore.isDatagen()) {
                RECIPES.add(context -> {
                    if (craftEight) {
                        RecipeBuilder.crafting(BetterEnd.C.mk(blockName), block)
                                     .outputCount(8)
                                     .shape("###", "#D#", "###")
                                     .addMaterial('#', source)
                                     .addMaterial('D', dyes.get(color))
                                     .build(context);
                    } else {
                        RecipeBuilder.crafting(BetterEnd.C.mk(blockName), block)
                                     .shapeless()
                                     .addMaterial('#', source)
                                     .addMaterial('D', dyes.get(color))
                                     .build(context);
                    }
                });
            }
            this.colors.put(color, block);
            BlocksHelper.addBlockColor(block, color);
        });
    }

    public Block getByColor(DyeColor color) {
        return colors.get(color.getMapColor().col);
    }

    public Block getByColor(int color) {
        return colors.get(color);
    }

    static {
        for (DyeColor color : DyeColor.values()) {
            int colorRGB = color.getMapColor().col;
            COLORS.put(colorRGB, color.getName());
            DYES.put(colorRGB, DyeItem.byColor(color));
        }
    }

    @Override
    public void registerRecipes(RecipeOutput context) {
        if (RECIPES != null) {
            RECIPES.forEach(r -> r.registerRecipes(context));
        }
    }

    @Override
    public void registerBlockTags(TagBootstrapContext<Block> context) {

    }

    @Override
    public void registerItemTags(ItemTagBootstrapContext context) {

    }
}
