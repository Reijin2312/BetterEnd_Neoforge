package org.betterx.betterend.item;

import org.betterx.betterend.interfaces.BetterEndElytra;
import org.betterx.betterend.interfaces.MultiModelItem;
import org.betterx.betterend.item.material.EndArmorTier;
import org.betterx.betterend.registry.EndItems;

public class CrystaliteElytra extends ArmoredElytra implements MultiModelItem, BetterEndElytra {
    public CrystaliteElytra(int durability, double movementFactor) {
        super("elytra_crystalite", EndArmorTier.CRYSTALITE, EndItems.ENCHANTED_MEMBRANE, durability, movementFactor, 1.2f, 1.25f, false);
    }
}
