package org.betterx.betterend.mixin.common;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(RecipeOutput.class)
public interface RecipeOutputMixin {
    @Unique
    default ResourceLocation getRecipeIdentifier(ResourceLocation id) {
        return id;
    }
}
