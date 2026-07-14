package org.betterx.betterend.registry;

import org.betterx.betterend.client.gui.EndStoneSmelterScreen;

import net.minecraft.client.gui.screens.MenuScreens;

public class EndScreens {
    public static void register() {
        MenuScreens.register(EndMenuTypes.END_STONE_SMELTER, EndStoneSmelterScreen::new);
    }
}
