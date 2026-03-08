package org.betterx.betterend.world.features.terrain;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;

public class ThinArchFeatureConfig implements FeatureConfiguration {
    public static final Codec<ThinArchFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    BlockStateProvider.CODEC.fieldOf("states").forGetter(o -> o.block)
            )
            .apply(instance, ThinArchFeatureConfig::new));


    public final BlockStateProvider block;

    public ThinArchFeatureConfig(Block block) {
        this(SimpleStateProvider.simple(block));
    }

    public ThinArchFeatureConfig(BlockStateProvider block) {
        this.block = block;
    }
}
