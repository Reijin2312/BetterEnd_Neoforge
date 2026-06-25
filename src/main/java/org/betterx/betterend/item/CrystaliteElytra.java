package org.betterx.betterend.item;

import org.betterx.bclib.client.render.HumanoidArmorRenderer;
import org.betterx.betterend.interfaces.BetterEndElytra;
import org.betterx.betterend.interfaces.MultiModelItem;
import org.betterx.betterend.item.material.EndArmorTier;
import org.betterx.betterend.item.model.CrystaliteArmorRenderer;
import org.betterx.betterend.registry.EndItems;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class CrystaliteElytra extends ArmoredElytra implements MultiModelItem, BetterEndElytra {
    private boolean clientInitDone;

    public CrystaliteElytra(int durability, double movementFactor) {
        super("elytra_crystalite", EndArmorTier.CRYSTALITE, EndItems.ENCHANTED_MEMBRANE, durability, movementFactor, 1.2f, 1.25f, false);
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        if (clientInitDone) {
            return;
        }
        clientInitDone = true;
        consumer.accept(new IClientItemExtensions() {
            @Override
            public Model getHumanoidArmorModel(
                    ItemStack itemStack,
                    EquipmentClientInfo.LayerType layerType,
                    Model original
            ) {
                if (layerType != EquipmentClientInfo.LayerType.HUMANOID) {
                    return original;
                }
                HumanoidModel<?> model = CrystaliteArmorRenderer.getInstance().modelFor(null, net.minecraft.world.entity.EquipmentSlot.CHEST);
                return model != null ? model : original;
            }

            @SuppressWarnings("unchecked")
            @Override
            public Model getGenericArmorModel(
                    ItemStack itemStack,
                    EquipmentClientInfo.LayerType layerType,
                    Model original
            ) {
                Model model = IClientItemExtensions.super.getGenericArmorModel(itemStack, layerType, original);
                if (model instanceof HumanoidArmorRenderer.CopyExtraState copy
                        && original instanceof HumanoidModel<?> originalHumanoid) {
                    copy.copyPropertiesFrom((HumanoidModel<?>) originalHumanoid);
                }
                return model;
            }
        });
    }
}
