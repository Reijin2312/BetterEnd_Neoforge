package org.betterx.betterend.registry;

import org.betterx.betterend.item.model.CrystaliteArmorRenderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class EndModelProviders {
    public final static void register() {
        CrystaliteArmorRenderer.register();
    }
}
