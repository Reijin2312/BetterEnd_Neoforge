package org.betterx.betterend.item;

import org.betterx.betterend.item.material.EndArmorTier;
import org.betterx.wover.complex.api.equipment.ArmorSlot;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class CrystaliteLeggings extends CrystaliteArmor {
    private static Properties defaultSettings() {
        return EndArmorItem.createDefaultEndArmorSettings(
                ArmorSlot.LEGGINGS_SLOT, EndArmorTier.CRYSTALITE,
                EndArmorItem.startAttributeBuilder(
                        ArmorSlot.LEGGINGS_SLOT,
                        EndArmorTier.CRYSTALITE
                ).add(
                        Attributes.MAX_HEALTH,
                        new AttributeModifier(
                                EndArmorItem.MAX_HEALTH_BOOST,
                                4.0,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.LEGS
                ).build()
        );
    }

    public CrystaliteLeggings() {
        super(Type.LEGGINGS, defaultSettings());
    }
}
