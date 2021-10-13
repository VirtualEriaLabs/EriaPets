package com.virtualeria.eriapets.client.particles;

import com.virtualeria.eriapets.utils.Constants;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ParticlesRegistry {

    public static final DefaultParticleType LIGHTING_PARTICLE = FabricParticleTypes.simple();

    public static void initializeServer(){
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Constants.ModID, "lighting_particle"), LIGHTING_PARTICLE);
    }
    public static void initializeClient(){
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(Constants.ModID, "particle/lighting_particle"));
        }));

        ParticleFactoryRegistry.getInstance().register(LIGHTING_PARTICLE, FlameParticle.Factory::new);
    }
}
