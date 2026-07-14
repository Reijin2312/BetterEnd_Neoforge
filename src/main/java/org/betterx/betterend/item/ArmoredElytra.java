package org.betterx.betterend.item;

import org.betterx.bclib.items.BaseArmorItem;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.interfaces.BetterEndElytra;
import org.betterx.betterend.interfaces.MultiModelItem;
import org.betterx.wover.complex.api.equipment.ArmorSlot;
import org.betterx.wover.complex.api.equipment.ArmorTier;
import org.betterx.wover.item.api.ItemTagProvider;
import org.betterx.wover.tag.api.event.context.ItemTagBootstrapContext;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ArmoredElytra extends BaseArmorItem implements MultiModelItem, BetterEndElytra, ItemTagProvider {
    private final ResourceLocation wingTexture;
    private final Item repairItem;
    private final double movementFactor;
    private final float toughness;
    private final int defense;

    private static Properties defaultSettings(
            ArmorTier material,
            int durability,
            float defenseDivider,
            float toughnessDivider,
            boolean fireproof
    ) {
        final float defense = material.armorMaterial
                .value()
                .getDefense(Type.CHESTPLATE) / defenseDivider;

        final float toughness = material.armorMaterial
                .value()
                .toughness() / toughnessDivider;

        final Properties props = EndArmorItem.createDefaultEndArmorSettings(
                                                     ArmorSlot.CHESTPLATE_SLOT, material,
                                                     EndArmorItem.startAttributeBuilder(
                                                             ArmorSlot.CHESTPLATE_SLOT,
                                                             material,
                                                             (int) defense, toughness, 0.5f
                                                     ).build()
                                             ).rarity(Rarity.EPIC)
                                             .durability(durability);
        if (fireproof) {
            props.fireResistant();
        }
        return props;
    }

    public ArmoredElytra(
            String name,
            ArmorTier material,
            Item repairItem,
            int durability,
            double movementFactor,
            float defenseDivider,
            float toughnessDivider,
            boolean fireproof
    ) {
        super(
                material.armorMaterial,
                Type.CHESTPLATE,
                defaultSettings(material, durability, defenseDivider, toughnessDivider, fireproof)
        );
        this.wingTexture = BetterEnd.C.mk("textures/entity/" + name + ".png");
        this.repairItem = repairItem;
        this.movementFactor = movementFactor;
        this.defense = (int) (material.armorMaterial
                .value()
                .getDefense(Type.CHESTPLATE) / defenseDivider);

        this.toughness = material.armorMaterial
                .value()
                .toughness() / toughnessDivider;

    }

    @Override
    public double getMovementFactor() {
        return movementFactor;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ResourceLocation getModelTexture() {
        return wingTexture;
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
        return super.isValidRepairItem(itemStack, itemStack2) || itemStack2.getItem() == repairItem;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerModelPredicate() {
        ItemProperties.register(
                this,
                ResourceLocation.withDefaultNamespace("broken"),
                (itemStack, clientLevel, livingEntity, id) -> ElytraItem.isFlyEnabled(itemStack) ? 0.0F : 1.0F
        );
    }

    @Override
    public void registerItemTags(ResourceLocation location, ItemTagBootstrapContext context) {
        context.add(this, ItemTags.DURABILITY_ENCHANTABLE, ItemTags.EQUIPPABLE_ENCHANTABLE);
    }
}
