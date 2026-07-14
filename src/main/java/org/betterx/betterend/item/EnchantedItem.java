package org.betterx.betterend.item;

import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.items.ModelProviderItem;
import org.betterx.betterend.registry.EndItems;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class EnchantedItem extends ModelProviderItem {

    private final Item source;

    public EnchantedItem(Item source) {
        super(EndItems.makeEndItemSettings().rarity(Rarity.RARE).stacksTo(16));
        this.source = source;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public BlockModel getItemModel(ResourceLocation resourceLocation) {
        ResourceLocation sourceId = BuiltInRegistries.ITEM.getKey(source);
        return ModelsHelper.createItemModel(sourceId);
    }
}
