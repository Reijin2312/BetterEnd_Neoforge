package org.betterx.betterend.registry;

import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.blocks.EndStoneSmelter;
import org.betterx.betterend.client.gui.EndStoneSmelterMenu;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.BiFunction;
import net.neoforged.neoforge.registries.RegisterEvent;

public class EndMenuTypes {
    public static MenuType<EndStoneSmelterMenu> END_STONE_SMELTER;

    public static void onRegister(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.MENU)) return;
        event.register(Registries.MENU, helper -> {
            END_STONE_SMELTER = registerSimple(BetterEnd.C.mk(EndStoneSmelter.ID), EndStoneSmelterMenu::new);
            helper.register(BetterEnd.C.mk(EndStoneSmelter.ID), END_STONE_SMELTER);
        });
    }

    static <T extends AbstractContainerMenu> MenuType<T> registerSimple(
            Identifier id,
            BiFunction<Integer, Inventory, T> factory
    ) {
        MenuType<T> type = new MenuType<>(factory::apply, FeatureFlags.DEFAULT_FLAGS);
        return type;
    }

    public static void ensureStaticallyLoaded() {
    }
}
