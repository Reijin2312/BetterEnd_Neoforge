package org.betterx.betterend.integration.rei;

import org.betterx.betterend.blocks.basis.EndAnvilBlock;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class REIAnvilCategory implements DisplayCategory<REIAnvilDisplay> {
    private final EntryStack<?>[] ANVILS;

    REIAnvilCategory(EntryStack<?>[] anvils) {
        ANVILS = anvils;
    }

    @Override
    public CategoryIdentifier<REIAnvilDisplay> getCategoryIdentifier() {
        return REIPlugin.SMITHING;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable(Blocks.ANVIL.getDescriptionId());
    }

    @Override
    public @NotNull EntryStack<?> getIcon() {
        return ANVILS[0];
    }


    @Override
    public @NotNull List<Widget> setupDisplay(REIAnvilDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.y + 10);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        int x = startPoint.x + 10;
        int y = startPoint.y;
        widgets.add(Widgets.createResultSlotBackground(new Point(x + 61, y + 5)));
        List<EntryIngredient> inputEntries = display.getInputEntries();
        EntryIngredient materials = inputEntries.get(1);
        int anvilLevel = display.getAnvilLevel();
        List<EntryStack<?>> anvils = Arrays.stream(ANVILS).filter(anvil -> {
            Object value = anvil.getValue();
            if (value instanceof ItemStack) {
                value = ((ItemStack) value).getItem();
            }
            Block block = ((BlockItem) value).getBlock();
            if (block instanceof EndAnvilBlock) {
                return ((EndAnvilBlock) block).getCraftingLevel() >= anvilLevel;
            }
            return anvilLevel == 1;
        }).collect(Collectors.toList());
        widgets.add(Widgets.createArrow(new Point(x + 24, y + 4)));
        widgets.add(Widgets.createLabel(
                new Point(bounds.x + bounds.width - 7, bounds.y + bounds.height - 15),
                Component.translatable("category.rei.damage.amount&dmg", display.getDamage())
        ).noShadow().rightAligned().color(0xFF404040, 0xFFBBBBBB));
        widgets.add(Widgets.createSlot(new Point(x - 20, y + 4)).entries(materials).markInput());
        widgets.add(Widgets.createSlot(new Point(x + 1, y + 4)).entries(inputEntries.get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(x + 61, y + 5))
                           .entries(display.getOutputEntries().get(0))
                           .disableBackground()
                           .markOutput());
        widgets.add(Widgets.createSlot(new Point(x - 9, y + 25)).entries(anvils));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 60;
    }
}