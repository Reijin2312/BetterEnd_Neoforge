package org.betterx.betterend.item;

import org.betterx.bclib.items.ModelProviderItem;
import org.betterx.betterend.registry.EndItems;

import net.minecraft.world.item.Rarity;

public class EternalCrystalItem extends ModelProviderItem {
    public EternalCrystalItem() {
        super(EndItems.makeEndItemSettings().stacksTo(16).rarity(Rarity.EPIC));
    }
}
