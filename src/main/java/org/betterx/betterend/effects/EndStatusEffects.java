package org.betterx.betterend.effects;

import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.effects.status.EndVeilEffect;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.ApiStatus;

public class EndStatusEffects {
    private static final ResourceKey<MobEffect> END_VEIL_KEY = ResourceKey.create(
            Registries.MOB_EFFECT,
            BetterEnd.C.mk("end_veil")
    );
    public final static MobEffectInstance CRYSTALITE_HEALTH_REGEN = new MobEffectInstance(
            MobEffects.REGENERATION,
            80,
            0,
            true,
            false,
            true
    );
    public final static MobEffectInstance CRYSTALITE_DIG_SPEED = new MobEffectInstance(
            MobEffects.HASTE,
            80,
            0,
            true,
            false,
            true
    );
    public final static MobEffectInstance CRYSTALITE_MOVE_SPEED = new MobEffectInstance(
            MobEffects.SPEED,
            80,
            0,
            true,
            false,
            true
    );

    public static Holder<MobEffect> END_VEIL;

    public static void onRegister(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.MOB_EFFECT)) return;
        event.register(Registries.MOB_EFFECT, helper -> {
            helper.register(END_VEIL_KEY.identifier(), new EndVeilEffect());
            END_VEIL = BuiltInRegistries.MOB_EFFECT.get(END_VEIL_KEY).orElseThrow();
        });
    }

    @ApiStatus.Internal
    public static void ensureStaticallyLoaded() {
        // NO-OP
    }
}
