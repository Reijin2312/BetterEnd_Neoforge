package org.betterx.betterend.registry;

import org.betterx.bclib.particles.BCLParticleType;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.particle.*;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class EndParticles {
    public static final SimpleParticleType GLOWING_SPHERE = register("glowing_sphere");
    public static final SimpleParticleType PORTAL_SPHERE = register("portal_sphere");
    public static final ParticleType<InfusionParticleType> INFUSION = BCLParticleType.register(BetterEnd.C.mk("infusion"), InfusionParticleType.CODEC, InfusionParticleType.STREAM_CODEC);

    public static final SimpleParticleType SULPHUR_PARTICLE = register("sulphur_particle");
    public static final SimpleParticleType GEYSER_PARTICLE = registerFar("geyser_particle");
    public static final SimpleParticleType SNOWFLAKE = register("snowflake");
    public static final SimpleParticleType AMBER_SPHERE = register("amber_sphere");
    public static final SimpleParticleType BLACK_SPORE = register("black_spore");
    public static final SimpleParticleType TENANEA_PETAL = register("tenanea_petal");
    public static final SimpleParticleType JUNGLE_SPORE = register("jungle_spore");
    public static final SimpleParticleType FIREFLY = register("firefly");
    public static final SimpleParticleType SMARAGDANT = register("smaragdant_particle");

    public static void register() {
        ParticleFactoryRegistry.getInstance().register(GLOWING_SPHERE, ParticleGlowingSphere.FactoryGlowingSphere::new);
        ParticleFactoryRegistry.getInstance().register(PORTAL_SPHERE, PaticlePortalSphere.FactoryPortalSphere::new);
        ParticleFactoryRegistry.getInstance().register(INFUSION, InfusionParticle.InfusionFactory::new);
        ParticleFactoryRegistry.getInstance().register(SULPHUR_PARTICLE, ParticleSulphur.FactorySulphur::new);
        ParticleFactoryRegistry.getInstance().register(GEYSER_PARTICLE, ParticleGeyser.FactoryGeyser::new);
        ParticleFactoryRegistry.getInstance().register(SNOWFLAKE, ParticleSnowflake.FactorySnowflake::new);
        ParticleFactoryRegistry.getInstance().register(AMBER_SPHERE, ParticleGlowingSphere.FactoryGlowingSphere::new);
        ParticleFactoryRegistry.getInstance().register(BLACK_SPORE, ParticleBlackSpore.FactoryBlackSpore::new);
        ParticleFactoryRegistry.getInstance().register(TENANEA_PETAL, ParticleTenaneaPetal.FactoryTenaneaPetal::new);
        ParticleFactoryRegistry.getInstance().register(JUNGLE_SPORE, ParticleJungleSpore.FactoryJungleSpore::new);
        ParticleFactoryRegistry.getInstance().register(FIREFLY, FireflyParticle.FireflyParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(SMARAGDANT, SmaragdantParticle.SmaragdantParticleFactory::new);
    }

    private static SimpleParticleType register(String name) {
        return BCLParticleType.register(BetterEnd.C.mk(name));
    }

    private static SimpleParticleType registerFar(String name) {
        return BCLParticleType.register(BetterEnd.C.mk(name), true);
    }

    public static void ensureStaticallyLoadedServerside() {
    }
}
