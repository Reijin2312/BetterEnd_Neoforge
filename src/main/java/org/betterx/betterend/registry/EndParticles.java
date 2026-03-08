package org.betterx.betterend.registry;

import org.betterx.bclib.particles.BCLParticleType;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.particle.FireflyParticle;
import org.betterx.betterend.particle.InfusionParticle;
import org.betterx.betterend.particle.InfusionParticleType;
import org.betterx.betterend.particle.ParticleBlackSpore;
import org.betterx.betterend.particle.ParticleGeyser;
import org.betterx.betterend.particle.ParticleGlowingSphere;
import org.betterx.betterend.particle.ParticleJungleSpore;
import org.betterx.betterend.particle.ParticleSnowflake;
import org.betterx.betterend.particle.ParticleSulphur;
import org.betterx.betterend.particle.ParticleTenaneaPetal;
import org.betterx.betterend.particle.PaticlePortalSphere;
import org.betterx.betterend.particle.SmaragdantParticle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

public class EndParticles {
    public static SimpleParticleType GLOWING_SPHERE;
    public static SimpleParticleType PORTAL_SPHERE;
    public static ParticleType<InfusionParticleType> INFUSION;

    public static SimpleParticleType SULPHUR_PARTICLE;
    public static SimpleParticleType GEYSER_PARTICLE;
    public static SimpleParticleType SNOWFLAKE;
    public static SimpleParticleType AMBER_SPHERE;
    public static SimpleParticleType BLACK_SPORE;
    public static SimpleParticleType TENANEA_PETAL;
    public static SimpleParticleType JUNGLE_SPORE;
    public static SimpleParticleType FIREFLY;
    public static SimpleParticleType SMARAGDANT;
    private static boolean registered = false;

    public static void onRegister(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.PARTICLE_TYPE)) {
            return;
        }
        registerTypes();
    }

    private static void registerTypes() {
        if (registered) return;
        registered = true;
        GLOWING_SPHERE = register("glowing_sphere");
        PORTAL_SPHERE = register("portal_sphere");
        INFUSION = BCLParticleType.register(BetterEnd.C.mk("infusion"), InfusionParticleType.CODEC, InfusionParticleType.STREAM_CODEC);

        SULPHUR_PARTICLE = register("sulphur_particle");
        GEYSER_PARTICLE = registerFar("geyser_particle");
        SNOWFLAKE = register("snowflake");
        AMBER_SPHERE = register("amber_sphere");
        BLACK_SPORE = register("black_spore");
        TENANEA_PETAL = register("tenanea_petal");
        JUNGLE_SPORE = register("jungle_spore");
        FIREFLY = register("firefly");
        SMARAGDANT = register("smaragdant_particle");
    }

    public static void registerProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(GLOWING_SPHERE, ParticleGlowingSphere.FactoryGlowingSphere::new);
        event.registerSpriteSet(PORTAL_SPHERE, PaticlePortalSphere.FactoryPortalSphere::new);
        event.registerSpriteSet(INFUSION, InfusionParticle.InfusionFactory::new);
        event.registerSpriteSet(SULPHUR_PARTICLE, ParticleSulphur.FactorySulphur::new);
        event.registerSpriteSet(GEYSER_PARTICLE, ParticleGeyser.FactoryGeyser::new);
        event.registerSpriteSet(SNOWFLAKE, ParticleSnowflake.FactorySnowflake::new);
        event.registerSpriteSet(AMBER_SPHERE, ParticleGlowingSphere.FactoryGlowingSphere::new);
        event.registerSpriteSet(BLACK_SPORE, ParticleBlackSpore.FactoryBlackSpore::new);
        event.registerSpriteSet(TENANEA_PETAL, ParticleTenaneaPetal.FactoryTenaneaPetal::new);
        event.registerSpriteSet(JUNGLE_SPORE, ParticleJungleSpore.FactoryJungleSpore::new);
        event.registerSpriteSet(FIREFLY, FireflyParticle.FireflyParticleFactory::new);
        event.registerSpriteSet(SMARAGDANT, SmaragdantParticle.SmaragdantParticleFactory::new);
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
