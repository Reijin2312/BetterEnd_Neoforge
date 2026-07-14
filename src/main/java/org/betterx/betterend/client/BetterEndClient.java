package org.betterx.betterend.client;

import org.betterx.bclib.integration.modmenu.ModMenu;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.client.render.BetterEndSkyRenderer;
import org.betterx.betterend.config.Configs;
import org.betterx.betterend.config.screen.ConfigScreen;
import org.betterx.betterend.events.ItemTooltipCallback;
import org.betterx.betterend.interfaces.MultiModelItem;
import org.betterx.betterend.item.CrystaliteArmor;
import org.betterx.betterend.registry.*;
import org.betterx.betterend.world.generator.GeneratorOptions;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;

public class BetterEndClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EndBlockEntityRenders.register();
        EndScreens.register();
        EndParticles.register();
        EndEntitiesRenders.register();
        EndModelProviders.register();
        MultiModelItem.register();
        registerTooltips();


        ModMenu.addModMenuScreen(BetterEnd.C.modId, ConfigScreen::new);

        ResourceLocation checkFlowerId = ResourceLocation.withDefaultNamespace("item/chorus_flower");
        ResourceLocation checkPlantId = ResourceLocation.withDefaultNamespace("item/chorus_plant");
        ResourceLocation toLoadFlowerId = BetterEnd.C.mk("item/custom_chorus_flower");
        ResourceLocation toLoadPlantId = BetterEnd.C.mk("item/custom_chorus_plant");


        ModelLoadingPlugin.register(pluginContext -> {
            pluginContext.resolveModel().register((context) -> {
                if (GeneratorOptions.changeChorusPlant()) {
                    if (context.id().equals(checkFlowerId)) {
                        return context.getOrLoadModel(toLoadFlowerId);
                    } else if (context.id().equals(checkPlantId)) {
                        return context.getOrLoadModel(toLoadPlantId);
                    }
                }
                return null;
            });
        });

        if (Configs.CLIENT_CONFIG.customSky.get()) {
            DimensionRenderingRegistry.registerSkyRenderer(Level.END, new BetterEndSkyRenderer());
        }
        if (BetterEnd.TRINKETS_CORE.isLoaded()) {
            org.betterx.betterend.integration.trinkets.ElytraClient.register();
        }
    }

    public static void registerTooltips() {
        ItemTooltipCallback.EVENT.register((player, stack, context, lines) -> {
            if (stack.getItem() instanceof CrystaliteArmor) {
                boolean hasSet = false;
                if (player != null) {
                    hasSet = CrystaliteArmor.hasFullSet(player);
                }
                MutableComponent setDesc = Component.translatable("tooltip.armor.crystalite_set");

                setDesc.setStyle(Style.EMPTY.applyFormats(
                        hasSet ? ChatFormatting.BLUE : ChatFormatting.DARK_GRAY,
                        ChatFormatting.ITALIC
                ));
                lines.add(Component.empty());
                lines.add(setDesc);
            }
        });
    }
}
