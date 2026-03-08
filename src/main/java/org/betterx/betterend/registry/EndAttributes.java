package org.betterx.betterend.registry;

import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.item.EndAttribute;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import org.jetbrains.annotations.ApiStatus;

public class EndAttributes {
    public final static Holder<Attribute> BLINDNESS_RESISTANCE = registerAttribute("generic.blindness_resistance", 0.0, true);

    public static Holder<Attribute> registerAttribute(String name, double value, boolean syncable) {
        return Registry.registerForHolder(
                BuiltInRegistries.ATTRIBUTE,
                BetterEnd.C.mk(name),
                new EndAttribute("attribute.name." + name, value).setSyncable(syncable)
        );
    }

    @ApiStatus.Internal
    public static AttributeSupplier.Builder addLivingEntityAttributes(AttributeSupplier.Builder builder) {
        return builder.add(EndAttributes.BLINDNESS_RESISTANCE);
    }
}
