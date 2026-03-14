package org.betterx.betterend.item;

import org.betterx.bclib.client.render.HumanoidArmorRenderer;
import org.betterx.betterend.interfaces.BetterEndElytra;
import org.betterx.betterend.interfaces.MultiModelItem;
import org.betterx.betterend.item.material.EndArmorTier;
import org.betterx.betterend.item.model.CrystaliteArmorRenderer;
import org.betterx.betterend.registry.EndItems;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class CrystaliteElytra extends ArmoredElytra implements MultiModelItem, BetterEndElytra {
    private boolean clientInitDone;

    public CrystaliteElytra(int durability, double movementFactor) {
        super("elytra_crystalite", EndArmorTier.CRYSTALITE, EndItems.ENCHANTED_MEMBRANE, durability, movementFactor, 1.2f, 1.25f, false);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        if (clientInitDone) {
            return;
        }
        clientInitDone = true;
        consumer.accept(new IClientItemExtensions() {
            @Override
            public HumanoidModel<?> getHumanoidArmorModel(
                    LivingEntity livingEntity,
                    ItemStack stack,
                    EquipmentSlot slot,
                    HumanoidModel<?> original
            ) {
                if (slot != EquipmentSlot.CHEST) {
                    return original;
                }
                CrystaliteArmorRenderer renderer = CrystaliteArmorRenderer.getInstance();
                HumanoidModel<LivingEntity> model = renderer.modelFor(livingEntity, slot);
                if (model == null) {
                    return original;
                }

                @SuppressWarnings("unchecked")
                HumanoidModel<LivingEntity> originalTyped = (HumanoidModel<LivingEntity>) original;
                originalTyped.copyPropertiesTo(model);
                if (model instanceof HumanoidArmorRenderer.CopyExtraState copy) {
                    copy.copyPropertiesFrom(originalTyped);
                }
                return model;
            }
        });
    }
}
