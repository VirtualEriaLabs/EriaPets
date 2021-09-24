package com.virtualeria.eriapets.client.renderer;

import com.virtualeria.eriapets.entities.EntityRegistryPets;
import com.virtualeria.eriapets.entities.SpumaEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;


public class RegisterRenderers {


    public static void onIntialize(){
        EntityRendererRegistry.register(EntityRegistryPets.BASE_PET_ENTITY, BasePetEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistryPets.OTHO_PET_ENTITY, OthoPetEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistryPets.SLIMER_PET_ENTITY, SlimerPetEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistryPets.PINGU_PET_ENTITY, PinguPetEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistryPets.FLINCH_PET_ENTITY, FlinchPetEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistryPets.GNOME_PET_ENTITY, GnomePetEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistryPets.CASPER_PET_ENTITY, CasperEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistryPets.USAGI_PET_ENTITY, UsagiPetEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistryPets.POCHO_PET_ENTITY, PochoEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistryPets.SPUMA_PET_ENTITY, SpumaEntityRenderer::new);
    }
}
