package org.betterx.betterend.registry;

import org.betterx.betterend.item.model.CrystaliteArmorRenderer;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndModelProviders {
    public final static void register() {
        CrystaliteArmorRenderer.getInstance();
    }
}
