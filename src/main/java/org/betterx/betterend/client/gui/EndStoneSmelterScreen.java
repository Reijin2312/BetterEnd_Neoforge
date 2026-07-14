package org.betterx.betterend.client.gui;

import org.betterx.betterend.BetterEnd;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class EndStoneSmelterScreen extends AbstractContainerScreen<EndStoneSmelterMenu> implements RecipeUpdateListener {
    private final static ResourceLocation BACKGROUND_TEXTURE = BetterEnd.C.mk("textures/gui/smelter_gui.png");

    public final EndStoneSmelterRecipeBookScreen recipeBook;
    private boolean narrow;

    public EndStoneSmelterScreen(EndStoneSmelterMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        recipeBook = new EndStoneSmelterRecipeBookScreen();
    }

    public void init() {
        super.init();
        narrow = width < 379;
        recipeBook.init(width, height, minecraft, narrow, menu);
        leftPos = recipeBook.updateScreenPosition(width, imageWidth);
        final var button = new ImageButton(
                leftPos + 20,
                height / 2 - 49,
                20,
                18,
                RecipeBookComponent.RECIPE_BUTTON_SPRITES,
                (buttonWidget) -> {
                    recipeBook.initVisuals();
                    recipeBook.toggleVisibility();
                    leftPos = recipeBook.updateScreenPosition(width, imageWidth);
                    buttonWidget.setPosition(leftPos + 20, height / 2 - 49);
                }
        );

        addRenderableWidget(button);
        titleLabelX = (imageWidth - font.width(title)) / 2;
    }

    @Override
    public void containerTick() {
        super.containerTick();
        recipeBook.tick();
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        if (recipeBook.isVisible() && narrow) {
            renderBg(guiGraphics, delta, mouseX, mouseY);
            recipeBook.render(guiGraphics, mouseX, mouseY, delta);
        } else {
            recipeBook.render(guiGraphics, mouseX, mouseY, delta);
            super.render(guiGraphics, mouseX, mouseY, delta);
            recipeBook.renderGhostRecipe(guiGraphics, leftPos, topPos, true, delta);
        }
        renderTooltip(guiGraphics, mouseX, mouseY);
        recipeBook.renderTooltip(guiGraphics, leftPos, topPos, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
            return true;
        } else {
            return narrow && recipeBook.isVisible() || super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    protected void slotClicked(Slot slot, int invSlot, int clickData, ClickType actionType) {
        super.slotClicked(slot, invSlot, clickData, actionType);
        this.recipeBook.slotClicked(slot);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return !recipeBook.keyPressed(keyCode, scanCode, modifiers) && super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top, int button) {
        boolean isMouseOut = mouseX < left || mouseY < top || mouseX >= (left + imageWidth) || mouseY >= (top + imageHeight);
        return this.recipeBook.hasClickedOutside(
                mouseX,
                mouseY,
                leftPos,
                topPos,
                imageWidth,
                imageHeight,
                button
        ) && isMouseOut;
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        return recipeBook.charTyped(chr, keyCode) || super.charTyped(chr, keyCode);
    }

    @Override
    public void recipesUpdated() {
        recipeBook.recipesUpdated();
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return recipeBook;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        if (minecraft == null) return;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(BACKGROUND_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        int progress;
        if (menu.isBurning()) {
            progress = menu.getFuelProgress();
            guiGraphics.blit(
                    BACKGROUND_TEXTURE,
                    leftPos + 56,
                    topPos + 36 + 12 - progress,
                    176,
                    12 - progress,
                    14,
                    progress + 1
            );
        }
        progress = menu.getSmeltProgress();
        guiGraphics.blit(BACKGROUND_TEXTURE, leftPos + 92, topPos + 34, 176, 14, progress + 1, 16);
    }
}
