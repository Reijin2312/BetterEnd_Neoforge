package org.betterx.betterend.integration.emi;

import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.registry.EndBlocks;

import net.minecraft.world.item.crafting.RecipeManager;

import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.stack.EmiStack;

public class EMIPlugin implements dev.emi.emi.api.EmiPlugin {
    public static final EmiStack INFUSION_WORKSTATION = EmiStack.of(EndBlocks.INFUSION_PEDESTAL);
    public static final EmiStack AZURE_JADESTONE_FURNACE_WORKSTATION = EmiStack.of(EndBlocks.AZURE_JADESTONE.furnace);
    public static final EmiStack SANDY_JADESTONE_FURNACE_WORKSTATION = EmiStack.of(EndBlocks.SANDY_JADESTONE.furnace);
    public static final EmiStack VIRID_JADESTONE_FURNACE_WORKSTATION = EmiStack.of(EndBlocks.VIRID_JADESTONE.furnace);

    public static final EmiRecipeCategory INFUSION_CATEGORY = new EmiRecipeCategory(
            BetterEnd.C.mk("infusion"),
            INFUSION_WORKSTATION,
            org.betterx.bclib.integration.emi.EMIPlugin.getSprite(0, 16)
    );

    @Override
    public void register(EmiRegistry emiRegistry) {
        final RecipeManager manager = emiRegistry.getRecipeManager();
        emiRegistry.addCategory(INFUSION_CATEGORY);
        emiRegistry.addWorkstation(INFUSION_CATEGORY, INFUSION_WORKSTATION);

        EMIInfusionRecipe.addAllRecipes(emiRegistry, manager);
        if (org.betterx.bclib.integration.emi.EMIPlugin.END_ALLOYING_CATEGORY != null) {
            EMIBlastingRecipe.addAllRecipes(emiRegistry, manager);
        }

        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, AZURE_JADESTONE_FURNACE_WORKSTATION);
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, SANDY_JADESTONE_FURNACE_WORKSTATION);
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, VIRID_JADESTONE_FURNACE_WORKSTATION);
    }
}
