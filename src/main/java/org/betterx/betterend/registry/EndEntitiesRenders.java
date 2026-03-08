package org.betterx.betterend.registry;

import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.entity.model.CubozoaEntityModel;
import org.betterx.betterend.entity.model.DragonflyEntityModel;
import org.betterx.betterend.entity.model.EndFishEntityModel;
import org.betterx.betterend.entity.model.EndSlimeEntityModel;
import org.betterx.betterend.entity.model.SilkMothEntityModel;
import org.betterx.betterend.entity.render.RendererEntityCubozoa;
import org.betterx.betterend.entity.render.RendererEntityDragonfly;
import org.betterx.betterend.entity.render.RendererEntityEndFish;
import org.betterx.betterend.entity.render.RendererEntityEndSlime;
import org.betterx.betterend.entity.render.RendererEntityShadowWalker;
import org.betterx.betterend.entity.render.SilkMothEntityRenderer;
import org.betterx.betterend.item.model.ArmoredElytraModel;
import org.betterx.betterend.item.model.CrystaliteBootsModel;
import org.betterx.betterend.item.model.CrystaliteChestplateModel;
import org.betterx.betterend.item.model.CrystaliteHelmetModel;
import org.betterx.betterend.item.model.CrystaliteLeggingsModel;

import net.minecraft.client.model.geom.ModelLayerLocation;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class EndEntitiesRenders {
    public static final ModelLayerLocation DRAGONFLY_MODEL = registerMain("dragonfly");
    public static final ModelLayerLocation END_SLIME_SHELL_MODEL = registerMain("endslime_shell");
    public static final ModelLayerLocation END_SLIME_MODEL = registerMain("endslime");
    public static final ModelLayerLocation END_FISH_MODEL = registerMain("endfish");
    public static final ModelLayerLocation CUBOZOA_MODEL = registerMain("cubozoa");
    public static final ModelLayerLocation SILK_MOTH_MODEL = registerMain("silkmoth");
    public static final ModelLayerLocation TEST_MODEL = registerMain("test");

    public static final ModelLayerLocation ARMORED_ELYTRA = registerMain("armored_elytra");
    public static final ModelLayerLocation CRYSTALITE_CHESTPLATE = registerMain("crystalite_chestplate");
    public static final ModelLayerLocation CRYSTALITE_CHESTPLATE_THIN = registerMain("crystalite_chestplate_thin");
    public static final ModelLayerLocation CRYSTALITE_HELMET = registerMain("crystalite_helmet");
    public static final ModelLayerLocation CRYSTALITE_LEGGINGS = registerMain("crystalite_leggings");
    public static final ModelLayerLocation CRYSTALITE_BOOTS = registerMain("crystalite_boots");

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EndEntities.DRAGONFLY.type(), RendererEntityDragonfly::new);
        event.registerEntityRenderer(EndEntities.END_SLIME.type(), RendererEntityEndSlime::new);
        event.registerEntityRenderer(EndEntities.END_FISH.type(), RendererEntityEndFish::new);
        event.registerEntityRenderer(EndEntities.CUBOZOA.type(), RendererEntityCubozoa::new);
        event.registerEntityRenderer(EndEntities.SHADOW_WALKER.type(), RendererEntityShadowWalker::new);
        event.registerEntityRenderer(EndEntities.SILK_MOTH.type(), SilkMothEntityRenderer::new);
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DRAGONFLY_MODEL, DragonflyEntityModel::getTexturedModelData);
        event.registerLayerDefinition(
                END_SLIME_SHELL_MODEL,
                EndSlimeEntityModel::getShellOnlyTexturedModelData
        );
        event.registerLayerDefinition(END_SLIME_MODEL, EndSlimeEntityModel::getCompleteTexturedModelData);
        event.registerLayerDefinition(END_FISH_MODEL, EndFishEntityModel::getTexturedModelData);
        event.registerLayerDefinition(CUBOZOA_MODEL, CubozoaEntityModel::getTexturedModelData);
        event.registerLayerDefinition(SILK_MOTH_MODEL, SilkMothEntityModel::getTexturedModelData);

        event.registerLayerDefinition(ARMORED_ELYTRA, ArmoredElytraModel::getTexturedModelData);
        event.registerLayerDefinition(
                CRYSTALITE_CHESTPLATE,
                CrystaliteChestplateModel::getRegularTexturedModelData
        );
        event.registerLayerDefinition(
                CRYSTALITE_CHESTPLATE_THIN,
                CrystaliteChestplateModel::getThinTexturedModelData
        );
        event.registerLayerDefinition(CRYSTALITE_HELMET, CrystaliteHelmetModel::getTexturedModelData);
        event.registerLayerDefinition(CRYSTALITE_LEGGINGS, CrystaliteLeggingsModel::getTexturedModelData);
        event.registerLayerDefinition(CRYSTALITE_BOOTS, CrystaliteBootsModel::getTexturedModelData);
    }

    private static ModelLayerLocation registerMain(String id) {
        return new ModelLayerLocation(BetterEnd.C.mk(id), "main");
    }
}
