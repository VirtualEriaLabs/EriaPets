package com.virtualeria.eriapets;

import com.virtualeria.eriapets.client.renderer.BasePetEntityRenderer;

import com.virtualeria.eriapets.entities.EntityRegistryPets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;


@Environment(EnvType.CLIENT)
public class EriaPetsMainClient  implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("[ERIAPETS CLIENT] Inizialitze client");
        EntityRendererRegistry.INSTANCE.register(EntityRegistryPets.BASE_PET_ENTITY, BasePetEntityRenderer::new);



    }
}
