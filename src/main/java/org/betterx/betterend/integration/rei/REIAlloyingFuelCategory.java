package org.betterx.betterend.integration.rei;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.DisplayRenderer;
import me.shedaniel.rei.api.client.gui.widgets.*;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;

import java.text.DecimalFormat;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class REIAlloyingFuelCategory implements DisplayCategory<REIAlloyingFuelDisplay> {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    @Override
    public @NotNull CategoryIdentifier getCategoryIdentifier() {
        return REIPlugin.ALLOYING_FUEL;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("category.rei.fuel");
    }

    @Override
    public int getDisplayHeight() {
        return 49;
    }

    @Override
    public @NotNull EntryStack getIcon() {
        return EntryStacks.of(Items.LAVA_BUCKET);
    }

    @Override
    public List<Widget> setupDisplay(REIAlloyingFuelDisplay recipeDisplay, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 17);
        String burnTime = DECIMAL_FORMAT.format(recipeDisplay.getFuelTime());
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createLabel(
                new Point(bounds.x + 26, bounds.getMaxY() - 15),
                Component.translatable("category.rei.fuel.time", burnTime)
        ).color(0xFF404040, 0xFFBBBBBB).noShadow().leftAligned());
        widgets.add(Widgets.createBurningFire(new Point(bounds.x + 6, startPoint.y + 1))
                           .animationDurationTicks(recipeDisplay.getFuelTime()));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 6, startPoint.y + 18))
                           .entries(recipeDisplay.getInputEntries().get(0))
                           .markInput());
        return widgets;
    }

    @Override
    public DisplayRenderer getDisplayRenderer(REIAlloyingFuelDisplay recipe) {
        Slot slot = Widgets.createSlot(new Point(0, 0))
                           .entries(recipe.getInputEntries().get(0))
                           .disableBackground()
                           .disableHighlight();
        String burnItems = DECIMAL_FORMAT.format(recipe.getFuelTime() / 200d);
        return new DisplayRenderer() {
            private final Component text = Component.translatable(
                    "category.rei.fuel.time_short.items",
                    burnItems
            );

            @Override
            public int getHeight() {
                return 22;
            }

            @Nullable
            @Override
            public Tooltip getTooltip(TooltipContext ctx) {
                if (slot.containsMouse(ctx.getPoint())) return slot.getCurrentTooltip(ctx);
                return null;
            }

            @Override
            public void render(GuiGraphics guiGraphics, Rectangle bounds, int mouseX, int mouseY, float delta) {
                //slot.setZ(getZ() + 50);
                slot.getBounds().setLocation(bounds.x + 4, bounds.y + 2);
                slot.render(guiGraphics, mouseX, mouseY, delta);
                guiGraphics.drawString(
                        Minecraft.getInstance().font,
                        text.getVisualOrderText(),
                        bounds.x + 25,
                        bounds.y + 8,
                        -1
                );
            }
        };
    }
}