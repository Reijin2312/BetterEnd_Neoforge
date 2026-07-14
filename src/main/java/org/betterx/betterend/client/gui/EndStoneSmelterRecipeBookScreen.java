package org.betterx.betterend.client.gui;

import org.betterx.betterend.blocks.entities.EndStoneSmelterBlockEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.recipebook.BlastingRecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class EndStoneSmelterRecipeBookScreen extends BlastingRecipeBookComponent {
    private Ingredient fuels;
    private Slot fuelSlot;

    @Override
    protected Set<Item> getFuelItems() {
        return EndStoneSmelterBlockEntity.availableFuels().keySet();
    }

    @Override
    public void slotClicked(Slot slot) {
        super.slotClicked(slot);
        if (slot != null && slot.index < this.menu.getSize()) {
            this.fuelSlot = null;
        }
    }

    @Override
    public void setupGhostRecipe(RecipeHolder<?> recipe, List<Slot> slots) {

        this.ghostRecipe.clear();
        ItemStack result = recipe.value().getResultItem(Minecraft.getInstance().level.registryAccess());
        this.ghostRecipe.setRecipe(recipe);
        this.ghostRecipe.addIngredient(Ingredient.of(result), (slots.get(3)).x, (slots.get(3)).y);
        NonNullList<Ingredient> inputs = recipe.value().getIngredients();

        this.fuelSlot = slots.get(2);
        if (fuelSlot.getItem().isEmpty()) {
            if (this.fuels == null) {
                this.fuels = Ingredient.of(this.getFuelItems().stream().map(ItemStack::new));
            }
            this.ghostRecipe.addIngredient(this.fuels, fuelSlot.x, fuelSlot.y);
        }

        Iterator<Ingredient> iterator = inputs.iterator();
        for (int i = 0; i < 2; i++) {
            if (!iterator.hasNext()) {
                return;
            }
            Ingredient ingredient = iterator.next();
            if (!ingredient.isEmpty()) {
                Slot slot = slots.get(i);
                this.ghostRecipe.addIngredient(ingredient, slot.x, slot.y);
            }
        }
    }
}
