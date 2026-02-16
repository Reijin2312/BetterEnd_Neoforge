package org.betterx.betterend.mixin.common;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.extensions.IRecipeOutputExtension;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(IRecipeOutputExtension.class)
public interface RecipeOutputExtensionMixin {
    @Unique
    default ResourceLocation getRecipeIdentifier(ResourceLocation id) {
        return id;
    }
}
