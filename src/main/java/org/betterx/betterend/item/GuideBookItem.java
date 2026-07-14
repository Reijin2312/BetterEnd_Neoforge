package org.betterx.betterend.item;

import org.betterx.bclib.items.ModelProviderItem;
import org.betterx.betterend.registry.EndItems;
import org.betterx.betterend.util.LangUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import org.jetbrains.annotations.ApiStatus;

public class GuideBookItem extends ModelProviderItem {
    public static final Item GUIDE_BOOK = EndItems.getItemRegistry().register("guidebook", new GuideBookItem());

    public static void register() {
    }

    public GuideBookItem() {
        super(EndItems.makeEndItemSettings().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        //TODO: 1.19.3 Re-Enable once patchouli is available
//        if (!world.isClientSide && user instanceof ServerPlayer) {
//            PatchouliAPI.get().openBookGUI((ServerPlayer) user, BOOK_ID);
//            return InteractionResultHolder.success(user.getItemInHand(hand));
//        }
        return InteractionResultHolder.consume(user.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(
            ItemStack itemStack,
            TooltipContext tooltipContext,
            List<Component> list,
            TooltipFlag tooltipFlag
    ) {
        list.add(LangUtil.getText("book.betterend", "subtitle")
                         .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
    }

    @ApiStatus.Internal
    public static final void ensureStaticallyLoaded() {
    }
}
