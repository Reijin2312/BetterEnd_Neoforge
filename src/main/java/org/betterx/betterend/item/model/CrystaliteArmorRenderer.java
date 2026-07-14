package org.betterx.betterend.item.model;

import org.betterx.bclib.client.render.HumanoidArmorRenderer;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.registry.EndItems;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;

import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class CrystaliteArmorRenderer extends HumanoidArmorRenderer {
    private final static ResourceLocation FIRST_LAYER = BetterEnd.C.mk(
            "textures/models/armor/crystalite_layer_1.png");
    private final static ResourceLocation SECOND_LAYER = BetterEnd.C.mk(
            "textures/models/armor/crystalite_layer_2.png");
    private final static CrystaliteHelmetModel HELMET_MODEL = CrystaliteHelmetModel.createModel(null);
    private final static CrystaliteChestplateModel CHEST_MODEL = CrystaliteChestplateModel.createRegularModel(null);
    private final static CrystaliteChestplateModel CHEST_MODEL_SLIM = CrystaliteChestplateModel.createThinModel(null);
    private final static CrystaliteLeggingsModel LEGGINGS_MODEL = CrystaliteLeggingsModel.createModel(null);
    private final static CrystaliteBootsModel BOOTS_MODEL = CrystaliteBootsModel.createModel(null);
    private static CrystaliteArmorRenderer INSTANCE = null;

    public static void register() {
        if (INSTANCE == null) {
            INSTANCE = new CrystaliteArmorRenderer();
            ArmorRenderer.register(
                    INSTANCE,
                    EndItems.CRYSTALITE_HELMET,
                    EndItems.CRYSTALITE_CHESTPLATE,
                    EndItems.CRYSTALITE_ELYTRA,
                    EndItems.CRYSTALITE_LEGGINGS,
                    EndItems.CRYSTALITE_BOOTS
            );
        }
    }

    @NotNull
    @Override
    protected ResourceLocation getTextureForSlot(EquipmentSlot slot, boolean innerLayer) {
        return innerLayer ? SECOND_LAYER : FIRST_LAYER;
    }

    @Override
    protected HumanoidModel<LivingEntity> getModelForSlot(LivingEntity entity, EquipmentSlot slot) {
        if (slot == EquipmentSlot.HEAD) return HELMET_MODEL;
        if (slot == EquipmentSlot.LEGS) return LEGGINGS_MODEL;
        if (slot == EquipmentSlot.FEET) return BOOTS_MODEL;
        if (slot == EquipmentSlot.CHEST) {
            if (entity instanceof AbstractClientPlayer acp && acp.getSkin().model().id().equals("slim")) {
                return CHEST_MODEL_SLIM;
            } else {
                return CHEST_MODEL;
            }
        }
        return null;
    }
}
