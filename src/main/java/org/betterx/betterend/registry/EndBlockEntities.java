package org.betterx.betterend.registry;

import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.blocks.EndStoneSmelter;
import org.betterx.betterend.blocks.basis.PedestalBlock;
import org.betterx.betterend.blocks.entities.*;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.neoforged.neoforge.registries.RegisterEvent;

public class EndBlockEntities {
    public static BlockEntityType<EndStoneSmelterBlockEntity> END_STONE_SMELTER;
    public static BlockEntityType<PedestalBlockEntity> PEDESTAL;
    public static BlockEntityType<EternalPedestalEntity> ETERNAL_PEDESTAL;
    public static BlockEntityType<InfusionPedestalEntity> INFUSION_PEDESTAL;
    public static BlockEntityType<BlockEntityHydrothermalVent> HYDROTHERMAL_VENT;

    public static void register() {
        // no-op; registration happens via RegisterEvent
    }

    public static void register(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.BLOCK_ENTITY_TYPE)) return;
        event.register(Registries.BLOCK_ENTITY_TYPE, helper -> {
            END_STONE_SMELTER = new BlockEntityType<>(
                    EndStoneSmelterBlockEntity::new,
                    EndBlocks.END_STONE_SMELTER
            );
            helper.register(BetterEnd.C.mk(EndStoneSmelter.ID), END_STONE_SMELTER);

            PEDESTAL = new BlockEntityType<>(
                    PedestalBlockEntity::new,
                    getPedestals()
            );
            helper.register(BetterEnd.C.mk("pedestal"), PEDESTAL);

            ETERNAL_PEDESTAL = new BlockEntityType<>(
                    EternalPedestalEntity::new,
                    EndBlocks.ETERNAL_PEDESTAL
            );
            helper.register(BetterEnd.C.mk("eternal_pedestal"), ETERNAL_PEDESTAL);

            INFUSION_PEDESTAL = new BlockEntityType<>(
                    InfusionPedestalEntity::new,
                    EndBlocks.INFUSION_PEDESTAL
            );
            helper.register(BetterEnd.C.mk("infusion_pedestal"), INFUSION_PEDESTAL);

            HYDROTHERMAL_VENT = new BlockEntityType<>(
                    BlockEntityHydrothermalVent::new,
                    EndBlocks.HYDROTHERMAL_VENT
            );
            helper.register(BetterEnd.C.mk("hydrother_malvent"), HYDROTHERMAL_VENT);
        });
    }

    static Block[] getPedestals() {
        return EndBlocks.getModBlocks()
                        .stream()
                        .filter(block -> block instanceof PedestalBlock && !((PedestalBlock) block).hasUniqueEntity())
                        .toArray(Block[]::new);
    }
}
