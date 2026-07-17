package org.betterx.betterend.world.structures.features;

import org.betterx.bclib.util.MHelper;
import org.betterx.betterend.registry.EndStructures;
import org.betterx.betterend.world.structures.piece.CrystalMountainPiece;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class MountainStructure extends FeatureBaseStructure {

    public MountainStructure(StructureSettings s) {
        super(s);
    }

    @Override
    public StructureType<MountainStructure> type() {
        return EndStructures.MOUNTAIN.type();
    }

    protected void generatePieces(StructurePiecesBuilder structurePiecesBuilder, GenerationContext context) {
        final RandomSource random = context.random();
        final ChunkPos chunkPos = context.chunkPos();
        final ChunkGenerator chunkGenerator = context.chunkGenerator();
        final LevelHeightAccessor levelHeightAccessor = context.heightAccessor();
        final RandomState rState = context.randomState();

        int x = chunkPos.getBlockX(MHelper.randRange(4, 12, random));
        int z = chunkPos.getBlockZ(MHelper.randRange(4, 12, random));
        int y = chunkGenerator.getBaseHeight(x, z, Types.WORLD_SURFACE_WG, levelHeightAccessor, rState);
        if (y > 5) {
            Holder<Biome> biome = getNoiseBiome(chunkGenerator, rState, x >> 2, y >> 2, z >> 2);
            float radius = MHelper.randRange(50, 100, random);
            float height = radius * MHelper.randRange(0.8F, 1.2F, random);
            CrystalMountainPiece piece = new CrystalMountainPiece(
                    new BlockPos(x, y, z),
                    radius,
                    height,
                    random,
                    biome
            );
            structurePiecesBuilder.addPiece(piece);
        }

        //this.calculateBoundingBox();
    }
}
