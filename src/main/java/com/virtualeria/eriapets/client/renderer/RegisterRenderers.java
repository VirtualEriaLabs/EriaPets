package com.virtualeria.eriapets.client.renderer;

import com.virtualeria.eriapets.entities.EntityRegistryPets;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class RegisterRenderers {

    public static void onIntialize(){
        EntityRendererRegistry.INSTANCE.register(EntityRegistryPets.BASE_PET_ENTITY, BasePetEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistryPets.OTHO_PET_ENTITY, OthoPetEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistryPets.SLIMER_PET_ENTITY, SlimerPetEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistryPets.PINGU_PET_ENTITY, PinguPetEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistryPets.FLINCH_PET_ENTITY, FlinchPetEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistryPets.GNOME_PET_ENTITY, GnomePetEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistryPets.CASPER_PET_ENTITY, CasperEntityRenderer::new);
    }
}
