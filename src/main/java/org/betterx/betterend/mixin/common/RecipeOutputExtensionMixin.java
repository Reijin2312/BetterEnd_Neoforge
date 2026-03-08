package org.betterx.betterend.mixin.common;

import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.extensions.IRecipeOutputExtension;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(IRecipeOutputExtension.class)
public interface RecipeOutputExtensionMixin {
    @Unique
    default Identifier getRecipeIdentifier(Identifier id) {
        return id;
    }
}
