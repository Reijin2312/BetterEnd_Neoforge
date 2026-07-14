package org.betterx.betterend.recipe.builders;

import org.betterx.bclib.interfaces.UnknownReceipBookCategory;
import org.betterx.bclib.recipes.BCLBaseRecipeBuilder;
import org.betterx.bclib.recipes.BCLRecipeManager;
import org.betterx.bclib.util.ItemUtil;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.rituals.InfusionRitual;
import org.betterx.wover.enchantment.api.EnchantmentUtils;
import org.betterx.wover.item.api.ItemStackHelper;
import org.betterx.wover.recipe.api.BaseRecipeBuilder;
import org.betterx.wover.recipe.api.BaseUnlockableRecipeBuilder;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Arrays;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InfusionRecipe implements Recipe<InfusionRitual.InfusionInput>, UnknownReceipBookCategory {
    public final static String GROUP = "infusion";
    public final static RecipeType<InfusionRecipe> TYPE = BCLRecipeManager.registerType(BetterEnd.MOD_ID, GROUP);
    public final static Serializer SERIALIZER = BCLRecipeManager.registerSerializer(
            BetterEnd.MOD_ID,
            GROUP,
            new Serializer()
    );

    private final Ingredient[] catalysts;
    final private Ingredient input;
    final private ItemStack output;
    final private int time;
    final private String group;

    private InfusionRecipe(Ingredient input, ItemStack output, Ingredient[] catalysts, int time, String group) {
        this.input = input;
        this.output = ItemStackHelper.callItemStackSetupIfPossible(output);
        this.catalysts = catalysts;
        this.time = time;
        this.group = group;
    }

    public static Builder create(String id, ItemLike output) {
        return create(BetterEnd.C.mk(id), output);
    }

    public static Builder create(ResourceLocation id, ItemLike output) {
        return new BuilderImpl(id, output);
    }

    public static Builder create(String id, ItemStack output) {
        return create(BetterEnd.C.mk(id), output);
    }

    public static Builder create(ResourceLocation id, ItemStack output) {
        return new BuilderImpl(id, output);
    }

    public static Builder create(
            String id,
            ResourceKey<Enchantment> enchantment,
            int level,
            HolderLookup.RegistryLookup<Enchantment> lookup
    ) {
        return create(BetterEnd.C.mk(id), enchantment, level, lookup);
    }

    public static Builder create(
            ResourceLocation id,
            ResourceKey<Enchantment> enchantment,
            int level,
            HolderLookup.RegistryLookup<Enchantment> lookup
    ) {
        return new BuilderImpl(id, createEnchantedBook(enchantment, level, lookup));
    }

    public static ItemStack createEnchantedBook(
            ResourceKey<Enchantment> enchantment,
            int level,
            HolderLookup.RegistryLookup<Enchantment> lookup
    ) {
        return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(EnchantmentUtils.getEnchantment(lookup, enchantment), level));
    }

    public int getInfusionTime() {
        return this.time;
    }

    @Override
    public boolean matches(InfusionRitual.InfusionInput inv, Level world) {
        boolean valid = this.input.test(inv.getItem(0));
        if (!valid) return false;
        for (int i = 0; i < 8; i++) {
            valid &= this.catalysts[i].test(inv.getItem(i + 1));
        }
        return valid;
    }

    @Override
    public @NotNull ItemStack assemble(InfusionRitual.InfusionInput recipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> defaultedList = NonNullList.create();
        defaultedList.add(input);
        defaultedList.addAll(Arrays.asList(catalysts));
        return defaultedList;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider acc) {
        return this.output;
    }


    @Override
    @Environment(EnvType.CLIENT)
    public @NotNull String getGroup() {
        return this.group;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return TYPE;
    }

    public interface Builder extends BaseRecipeBuilder<Builder>, BaseUnlockableRecipeBuilder<Builder> {
        Builder group(@Nullable String group);

        Builder setPrimaryInput(ItemLike... inputs);
        Builder setPrimaryInput(TagKey<Item> input);
        Builder setPrimaryInputAndUnlock(TagKey<Item> input);
        Builder setPrimaryInputAndUnlock(ItemLike... inputs);

        Builder setTime(int time);
        Builder addCatalyst(CatalystSlot slot, ItemLike... items);
        Builder addCatalyst(CatalystSlot slot, ItemStack stack);
        Builder addCatalyst(CatalystSlot slot, TagKey<Item> tag);
    }

    public static class BuilderImpl extends BCLBaseRecipeBuilder<Builder, InfusionRecipe> implements Builder {
        private final Ingredient[] catalysts;
        private int time;

        protected BuilderImpl(ResourceLocation id, ItemLike output) {
            this(id, new ItemStack(output, 1));
        }

        protected BuilderImpl(ResourceLocation id, ItemStack output) {
            super(id, output, false);
            this.catalysts = new Ingredient[]{
                    Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY,
                    Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY
            };
            this.time = 1;
        }


        public Builder setTime(int time) {
            this.time = time;
            return this;
        }

        public Builder addCatalyst(CatalystSlot slot, ItemLike... items) {
            this.catalysts[slot.index] = Ingredient.of(items);
            return this;
        }

        public Builder addCatalyst(CatalystSlot slot, ItemStack stack) {
            this.catalysts[slot.index] = Ingredient.of(stack);
            return this;
        }

        public Builder addCatalyst(CatalystSlot slot, TagKey<Item> tag) {
            this.catalysts[slot.index] = Ingredient.of(tag);
            return this;
        }

        @Override
        protected void validate() {
            super.validate();
            if (time < 0) {
                throwIllegalStateException(
                        "Time should be positive, recipe {} will be ignored!"
                );
            }
        }

        @Override
        protected InfusionRecipe createRecipe(ResourceLocation id) {
            return new InfusionRecipe(
                    this.primaryInput,
                    this.output,
                    this.catalysts,
                    this.time,
                    this.group
            );
        }
    }


    public enum CatalystSlot implements StringRepresentable {
        NORTH(0, "north"),
        NORTH_EAST(1, "north_east"),
        EAST(2, "east"),
        SOUTH_EAST(3, "south_east"),
        SOUTH(4, "south"),
        SOUTH_WEST(5, "south_west"),
        WEST(6, "west"),
        NORTH_WEST(7, "north_west");

        public static final Codec<CatalystSlot> CODEC = StringRepresentable.fromEnum(CatalystSlot::values);

        public final int index;
        public final String key;

        CatalystSlot(int index, String key) {
            this.index = index;
            this.key = key;
        }

        @Override
        public @NotNull String getSerializedName() {
            return key;
        }
    }

    public static class Serializer implements RecipeSerializer<InfusionRecipe> {
        public static @NotNull InfusionRecipe fromNetwork(RegistryFriendlyByteBuf packetBuffer) {
            final Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(packetBuffer);
            final ItemStack output = ItemStack.STREAM_CODEC.decode(packetBuffer);
            final String group = packetBuffer.readUtf();
            final int time = packetBuffer.readVarInt();
            final Ingredient[] catalysts = new Ingredient[8];
            for (int i = 0; i < 8; i++) {
                catalysts[i] = Ingredient.CONTENTS_STREAM_CODEC.decode(packetBuffer);
            }
            return new InfusionRecipe(input, output, catalysts, time, group);
        }

        public static void toNetwork(RegistryFriendlyByteBuf packetBuffer, InfusionRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(packetBuffer, recipe.input);
            ItemStack.STREAM_CODEC.encode(packetBuffer, recipe.output);
            packetBuffer.writeUtf(recipe.group);
            packetBuffer.writeVarInt(recipe.time);
            for (int i = 0; i < 8; i++) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(packetBuffer, recipe.catalysts[i]);
            }
        }

        public static final MapCodec<Ingredient[]> CODEC_CATALYSTS = RecordCodecBuilder.mapCodec(instance -> instance
                .group(
                        ItemUtil.CODEC_INGREDIENT_WITH_NBT
                                .lenientOptionalFieldOf(CatalystSlot.NORTH.key, Ingredient.EMPTY)
                                .forGetter(catalysts -> catalysts[CatalystSlot.NORTH.index]),
                        ItemUtil.CODEC_INGREDIENT_WITH_NBT
                                .lenientOptionalFieldOf(CatalystSlot.NORTH_EAST.key, Ingredient.EMPTY)
                                .forGetter(catalysts -> catalysts[CatalystSlot.NORTH_EAST.index]),
                        ItemUtil.CODEC_INGREDIENT_WITH_NBT
                                .lenientOptionalFieldOf(CatalystSlot.EAST.key, Ingredient.EMPTY)
                                .forGetter(catalysts -> catalysts[CatalystSlot.EAST.index]),
                        ItemUtil.CODEC_INGREDIENT_WITH_NBT
                                .lenientOptionalFieldOf(CatalystSlot.SOUTH_EAST.key, Ingredient.EMPTY)
                                .forGetter(catalysts -> catalysts[CatalystSlot.SOUTH_EAST.index]),
                        ItemUtil.CODEC_INGREDIENT_WITH_NBT
                                .lenientOptionalFieldOf(CatalystSlot.SOUTH.key, Ingredient.EMPTY)
                                .forGetter(catalysts -> catalysts[CatalystSlot.SOUTH.index]),
                        ItemUtil.CODEC_INGREDIENT_WITH_NBT
                                .lenientOptionalFieldOf(CatalystSlot.SOUTH_WEST.key, Ingredient.EMPTY)
                                .forGetter(catalysts -> catalysts[CatalystSlot.SOUTH_WEST.index]),
                        ItemUtil.CODEC_INGREDIENT_WITH_NBT
                                .lenientOptionalFieldOf(CatalystSlot.WEST.key, Ingredient.EMPTY)
                                .forGetter(catalysts -> catalysts[CatalystSlot.WEST.index]),
                        ItemUtil.CODEC_INGREDIENT_WITH_NBT
                                .lenientOptionalFieldOf(CatalystSlot.NORTH_WEST.key, Ingredient.EMPTY)
                                .forGetter(catalysts -> catalysts[CatalystSlot.NORTH_WEST.index])
                )
                .apply(instance, (n, ne, e, se, s, sw, w, nw) -> new Ingredient[]{n, ne, e, se, s, sw, w, nw}));

        public static final MapCodec<InfusionRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ItemUtil.CODEC_INGREDIENT_WITH_NBT.fieldOf("input").forGetter(recipe -> recipe.input),
                ItemUtil.CODEC_ITEM_STACK_WITH_NBT.fieldOf("result").forGetter(recipe -> recipe.output),
                CODEC_CATALYSTS.fieldOf("catalysts").forGetter(recipe -> recipe.catalysts),
                Codec.INT.optionalFieldOf("time", 1).forGetter(recipe -> recipe.time),
                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group)
        ).apply(instance, InfusionRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, InfusionRecipe> STREAM_CODEC = StreamCodec.of(InfusionRecipe.Serializer::toNetwork, InfusionRecipe.Serializer::fromNetwork);


        @Override
        public @NotNull MapCodec<InfusionRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, InfusionRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static void register() {
        //we call this to make sure that TYPE is initialized
    }
}
