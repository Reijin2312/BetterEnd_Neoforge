package org.betterx.betterend.item;

import org.betterx.bclib.items.BaseArmorItem;
import org.betterx.bclib.client.render.HumanoidArmorRenderer;
import org.betterx.betterend.effects.EndStatusEffects;
import org.betterx.betterend.item.model.CrystaliteArmorRenderer;
import org.betterx.betterend.item.material.EndArmorMaterial;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class CrystaliteArmor extends BaseArmorItem {
    public final static MutableComponent CHEST_DESC;
    public final static MutableComponent BOOTS_DESC;
    private boolean clientInitDone;

    public CrystaliteArmor(ArmorType type, Properties settings) {
        super(EndArmorMaterial.CRYSTALITE, type, settings);
    }

    public static boolean hasFullSet(LivingEntity owner) {
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack armorStack = owner.getItemBySlot(slot);
            if (!(armorStack.getItem() instanceof CrystaliteArmor)) {
                return false;
            }
        }
        return true;
    }

    public static void applySetEffect(LivingEntity owner) {
        if ((owner.tickCount & 63) == 0) {
            owner.addEffect(new MobEffectInstance(EndStatusEffects.CRYSTALITE_HEALTH_REGEN));
        }
    }

    static {
        Style descStyle = Style.EMPTY.applyFormats(ChatFormatting.DARK_AQUA, ChatFormatting.ITALIC);
        CHEST_DESC = Component.translatable("tooltip.armor.crystalite_chest");
        CHEST_DESC.setStyle(descStyle);
        BOOTS_DESC = Component.translatable("tooltip.armor.crystalite_boots");
        BOOTS_DESC.setStyle(descStyle);
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        if (!clientInitDone) {
            clientInitDone = true;
            consumer.accept(new IClientItemExtensions() {
                @Override
                public Model getHumanoidArmorModel(
                        ItemStack itemStack,
                        EquipmentClientInfo.LayerType layerType,
                        Model original
                ) {
                    if (layerType != EquipmentClientInfo.LayerType.HUMANOID
                            && layerType != EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS) {
                        return original;
                    }
                    EquipmentSlot slot = slotFor(itemStack, layerType);
                    HumanoidModel<?> model = CrystaliteArmorRenderer.getInstance().modelFor(null, slot);
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

    private static EquipmentSlot slotFor(ItemStack itemStack, EquipmentClientInfo.LayerType layerType) {
        Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
        if (equippable != null) {
            return equippable.slot();
        }
        return layerType == EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS
                ? EquipmentSlot.LEGS
                : EquipmentSlot.CHEST;
    }
}
