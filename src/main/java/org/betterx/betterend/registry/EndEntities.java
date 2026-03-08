package org.betterx.betterend.registry;

import org.betterx.bclib.api.v2.spawning.SpawnRuleBuilder;
import org.betterx.bclib.entity.BCLEntityWrapper;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.entity.*;
import org.betterx.ui.ColorUtil;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap.Types;

import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import com.google.common.collect.Maps;

import java.util.Map;

public class EndEntities {
    public static final Identifier DRAGONFLY_ID = BetterEnd.C.mk("dragonfly");
    public static final Identifier END_SLIME_ID = BetterEnd.C.mk("end_slime");
    public static final Identifier END_FISH_ID = BetterEnd.C.mk("end_fish");
    public static final Identifier SHADOW_WALKER_ID = BetterEnd.C.mk("shadow_walker");
    public static final Identifier CUBOZOA_ID = BetterEnd.C.mk("cubozoa");
    public static final Identifier SILK_MOTH_ID = BetterEnd.C.mk("silk_moth");

    private static final EntityType<DragonflyEntity> DRAGONFLY_TYPE = buildEntityType(
            DragonflyEntity::new, MobCategory.AMBIENT, 0.6F, 0.5F, DRAGONFLY_ID
    );
    private static final EntityType<EndSlimeEntity> END_SLIME_TYPE = buildEntityType(
            EndSlimeEntity::new, MobCategory.MONSTER, 0.5F, 0.5F, END_SLIME_ID
    );
    private static final EntityType<EndFishEntity> END_FISH_TYPE = buildEntityType(
            EndFishEntity::new, MobCategory.WATER_AMBIENT, 0.5F, 0.5F, END_FISH_ID
    );
    private static final EntityType<ShadowWalkerEntity> SHADOW_WALKER_TYPE = buildEntityType(
            ShadowWalkerEntity::new, MobCategory.MONSTER, 0.6F, 1.95F, SHADOW_WALKER_ID
    );
    private static final EntityType<CubozoaEntity> CUBOZOA_TYPE = buildEntityType(
            CubozoaEntity::new, MobCategory.WATER_AMBIENT, 0.6F, 1F, CUBOZOA_ID
    );
    private static final EntityType<SilkMothEntity> SILK_MOTH_TYPE = buildEntityType(
            SilkMothEntity::new, MobCategory.AMBIENT, 0.6F, 0.6F, SILK_MOTH_ID
    );

    public static final BCLEntityWrapper<DragonflyEntity> DRAGONFLY = new BCLEntityWrapper<>(DRAGONFLY_TYPE, true);
    public static final BCLEntityWrapper<EndSlimeEntity> END_SLIME = new BCLEntityWrapper<>(END_SLIME_TYPE, true);
    public static final BCLEntityWrapper<EndFishEntity> END_FISH = new BCLEntityWrapper<>(END_FISH_TYPE, true);
    public static final BCLEntityWrapper<ShadowWalkerEntity> SHADOW_WALKER = new BCLEntityWrapper<>(SHADOW_WALKER_TYPE, true);
    public static final BCLEntityWrapper<CubozoaEntity> CUBOZOA = new BCLEntityWrapper<>(CUBOZOA_TYPE, true);
    public static final BCLEntityWrapper<SilkMothEntity> SILK_MOTH = new BCLEntityWrapper<>(SILK_MOTH_TYPE, true);

    private static final Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> ATTR_BUILDERS = Maps.newHashMap();

    public static void onRegister(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.ENTITY_TYPE)) return;
        event.register(Registries.ENTITY_TYPE, helper -> {
            registerMob(helper, DRAGONFLY_ID, DRAGONFLY_TYPE, DragonflyEntity.createMobAttributes(), ColorUtil.color(32, 42, 176), ColorUtil.color(115, 225, 249));
            registerMob(helper, END_SLIME_ID, END_SLIME_TYPE, EndSlimeEntity.createMobAttributes(), ColorUtil.color(28, 28, 28), ColorUtil.color(99, 11, 99));
            registerMob(helper, END_FISH_ID, END_FISH_TYPE, EndFishEntity.createMobAttributes(), ColorUtil.color(3, 50, 76), ColorUtil.color(120, 206, 255));
            registerMob(helper, SHADOW_WALKER_ID, SHADOW_WALKER_TYPE, ShadowWalkerEntity.createMobAttributes(), ColorUtil.color(30, 30, 30), ColorUtil.color(5, 5, 5));
            registerMob(helper, CUBOZOA_ID, CUBOZOA_TYPE, CubozoaEntity.createMobAttributes(), ColorUtil.color(151, 77, 181), ColorUtil.color(93, 176, 238));
            registerMob(helper, SILK_MOTH_ID, SILK_MOTH_TYPE, SilkMothEntity.createMobAttributes(), ColorUtil.color(198, 138, 204), ColorUtil.color(242, 220, 236));

            setupSpawnRules();
        });
    }

    public static void onRegisterAttributes(EntityAttributeCreationEvent event) {
        ATTR_BUILDERS.forEach((type, builder) -> event.put(type, builder.build()));
    }

    public static void register() {
        setupSpawnRules();
    }

    private static void setupSpawnRules() {
        if (DRAGONFLY == null || SILK_MOTH == null || END_SLIME == null || SHADOW_WALKER == null || END_FISH == null || CUBOZOA == null) {
            return;
        }
        // Air //
        SpawnRuleBuilder.start(DRAGONFLY).aboveGround(2).maxNearby(4, 32).buildNoRestrictions(Types.MOTION_BLOCKING);
        SpawnRuleBuilder.start(SILK_MOTH).aboveGround(2).maxNearby(4, 32).buildNoRestrictions(Types.MOTION_BLOCKING);

        // Land //
        SpawnRuleBuilder
                .start(END_SLIME)
                .notPeaceful()
                .maxNearby(6, 32)
                .onlyOnValidBlocks()
                .customRule(EndSlimeEntity::canSpawn)
                .buildOnGround(Types.MOTION_BLOCKING_NO_LEAVES);

        SpawnRuleBuilder.start(SHADOW_WALKER)
                        .vanillaHostile()
                        .onlyOnValidBlocks()
                        .maxNearby(8, 64)
                        .buildOnGround(Types.MOTION_BLOCKING);

        // Water //
        SpawnRuleBuilder.start(END_FISH).maxNearby(16, 16).buildInWater(Types.MOTION_BLOCKING_NO_LEAVES);
        SpawnRuleBuilder.start(CUBOZOA).maxNearby(16, 16).buildInWater(Types.MOTION_BLOCKING_NO_LEAVES);
    }

    private static <T extends Mob> void registerMob(
            RegisterEvent.RegisterHelper<EntityType<?>> helper,
            Identifier id,
            EntityType<T> type,
            AttributeSupplier.Builder attributes,
            int eggColor,
            int dotsColor
    ) {
        registerAttribute(type, attributes);
        EndItems.registerEndEgg("spawn_egg_" + id.getPath(), type, eggColor, dotsColor);
        helper.register(id, type);
    }

    private static <T extends Mob> EntityType<T> buildEntityType(
            EntityType.EntityFactory<T> factory,
            MobCategory category,
            float width,
            float height,
            Identifier id
    ) {
        return EntityType.Builder
                .of(factory, category)
                .sized(width, height)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, id));
    }

    private static void registerAttribute(EntityType<? extends LivingEntity> entity, AttributeSupplier.Builder builder) {
        ATTR_BUILDERS.put(entity, builder);
    }
}
