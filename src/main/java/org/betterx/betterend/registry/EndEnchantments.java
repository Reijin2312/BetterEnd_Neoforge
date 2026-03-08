package org.betterx.betterend.registry;

import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.effects.EndStatusEffects;
import org.betterx.wover.enchantment.api.EnchantmentKey;
import org.betterx.wover.enchantment.api.EnchantmentManager;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;

import net.neoforged.neoforge.registries.RegisterEvent;

import org.jetbrains.annotations.ApiStatus;

public class EndEnchantments {
    public final static EnchantmentKey END_VEIL = EnchantmentManager.createKey(BetterEnd.C.mk("end_veil"));

    private static final ResourceKey<DataComponentType<?>> END_VEIL_STATE_KEY = ResourceKey.create(
            Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE,
            BetterEnd.C.mk("end_veil")
    );
    public static DataComponentType<Unit> END_VEIL_STATE;

    public static void onRegister(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE)) return;
        event.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, helper -> {
            final DataComponentType<Unit> type = DataComponentType.<Unit>builder()
                    .persistent(Unit.CODEC)
                    .build();
            helper.register(END_VEIL_STATE_KEY.identifier(), type);
            END_VEIL_STATE = type;
        });
    }

    public static DataComponentType<Unit> getEndVeilState() {
        if (END_VEIL_STATE != null) return END_VEIL_STATE;
        return getFromRegistry();
    }

    @SuppressWarnings("unchecked")
    private static DataComponentType<Unit> getFromRegistry() {
        return (DataComponentType<Unit>) BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE
                .get(END_VEIL_STATE_KEY)
                .map(Holder.Reference::value)
                .orElse(null);
    }

    @ApiStatus.Internal
    public static void ensureStaticallyLoaded() {
        EndStatusEffects.ensureStaticallyLoaded();
    }
}
