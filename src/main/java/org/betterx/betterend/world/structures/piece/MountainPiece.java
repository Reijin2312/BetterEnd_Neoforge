package org.betterx.betterend.world.structures.piece;

import org.betterx.bclib.util.MHelper;
import org.betterx.betterend.noise.OpenSimplexNoise;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import com.google.common.collect.Maps;

import java.util.Map;

public abstract class MountainPiece extends BasePiece {
    protected Map<Long, Integer> heightmap = Maps.newConcurrentMap();
    private volatile boolean heightmapPreloaded;
    protected OpenSimplexNoise noise1;
    protected OpenSimplexNoise noise2;
    protected BlockPos center;
    protected float radius;
    protected float height;
    protected float r2;
    protected ResourceKey<Biome> biomeID;
    protected int seed1;
    protected int seed2;

    public MountainPiece(
            StructurePieceType type,
            BlockPos center,
            float radius,
            float height,
            RandomSource random,
            Holder<Biome> biome
    ) {
        super(type, random.nextInt(), null);
        this.center = center;
        this.radius = radius;
        this.height = height;
        this.r2 = radius * radius;
        this.seed1 = random.nextInt();
        this.seed2 = random.nextInt();
        this.noise1 = new OpenSimplexNoise(this.seed1);
        this.noise2 = new OpenSimplexNoise(this.seed2);
        this.biomeID = biome.unwrapKey().orElse(null);
        makeBoundingBox();
    }

    public MountainPiece(StructurePieceType type, CompoundTag tag) {
        super(type, tag);
        makeBoundingBox();
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.put("center", NbtUtils.writeBlockPos(center));
        tag.putFloat("radius", radius);
        tag.putFloat("height", height);
        tag.putString("biome", biomeID.location().toString());
        tag.putInt("seed1", seed1);
        tag.putInt("seed2", seed2);
    }

    @Override
    protected void fromNbt(CompoundTag tag) {
        center = NbtUtils.readBlockPos(tag, "center").orElse(BlockPos.ZERO);
        radius = tag.getFloat("radius");
        height = tag.getFloat("height");
        biomeID = ResourceKey.create(Registries.BIOME, ResourceLocation.parse(tag.getString("biome")));
        r2 = radius * radius;
        seed1 = tag.getInt("seed1");
        seed2 = tag.getInt("seed2");
        noise1 = new OpenSimplexNoise(seed1);
        noise2 = new OpenSimplexNoise(seed2);
    }

    private int getHeight(WorldGenLevel world, BlockPos pos) {
        long p = getCacheKey(pos);
        Integer cached = heightmap.get(p);
        if (cached != null) {
            return cached;
        }

        int h;
        if (!world.getBiome(pos).is(biomeID)) {
            h = -10;
            Integer previous = heightmap.putIfAbsent(p, h);
            return previous == null ? h : previous;
        }
        h = world.getHeight(Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
        h = Mth.abs(h - center.getY());
        if (h > 4) {
            h = 4 - h;
            Integer previous = heightmap.putIfAbsent(p, h);
            return previous == null ? h : previous;
        }

        h = MHelper.floor(noise2.eval(pos.getX() * 0.01, pos.getZ() * 0.01) * noise2.eval(
                pos.getX() * 0.002,
                pos.getZ() * 0.002
        ) * 8 + 8);

        if (h < 0) {
            h = 0;
            Integer previous = heightmap.putIfAbsent(p, h);
            return previous == null ? h : previous;
        }

        Integer previous = heightmap.putIfAbsent(p, h);
        return previous == null ? h : previous;
    }

    protected void preloadHeightmap(WorldGenLevel world, int clampRadius) {
        if (heightmapPreloaded) {
            return;
        }
        synchronized (heightmap) {
            if (heightmapPreloaded) {
                return;
            }
            MutableBlockPos mut = new MutableBlockPos();
            int minX = boundingBox.minX() - clampRadius;
            int maxX = boundingBox.maxX() + clampRadius;
            int minZ = boundingBox.minZ() - clampRadius;
            int maxZ = boundingBox.maxZ() + clampRadius;
            mut.setY(center.getY());
            for (int x = minX; x <= maxX; x++) {
                mut.setX(x);
                for (int z = minZ; z <= maxZ; z++) {
                    mut.setZ(z);
                    getHeight(world, mut);
                }
            }
            heightmapPreloaded = true;
        }
    }

    protected float getHeightClamp(WorldGenLevel world, int radius, int posX, int posZ) {
        MutableBlockPos mut = new MutableBlockPos();
        mut.setY(center.getY());
        float height = 0;
        float max = 0;
        for (int x = -radius; x <= radius; x++) {
            mut.setX(posX + x);
            int x2 = x * x;
            for (int z = -radius; z <= radius; z++) {
                mut.setZ(posZ + z);
                int z2 = z * z;
                float mult = 1 - (float) Math.sqrt(x2 + z2) / radius;
                if (mult > 0) {
                    max += mult;
                    height += getHeight(world, mut) * mult;
                }
            }
        }
        height /= max;
        return Mth.clamp(height / radius, 0, 1);
    }

    private long getCacheKey(BlockPos pos) {
        return (((long) pos.getX()) << 32) ^ (pos.getZ() & 0xFFFFFFFFL);
    }

    private void makeBoundingBox() {
        int minX = MHelper.floor(center.getX() - radius);
        int minY = MHelper.floor(center.getY() - radius);
        int minZ = MHelper.floor(center.getZ() - radius);
        int maxX = MHelper.floor(center.getX() + radius + 1);
        int maxY = MHelper.floor(center.getY() + radius + 1);
        int maxZ = MHelper.floor(center.getZ() + radius + 1);
        this.boundingBox = new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
