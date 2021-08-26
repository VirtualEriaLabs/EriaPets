package com.virtualeria.eriapets;

import com.virtualeria.eriapets.client.gui.PetGuiDescription;
import com.virtualeria.eriapets.client.gui.PetScreen;
import com.virtualeria.eriapets.client.renderer.BasePetEntityRenderer;

import com.virtualeria.eriapets.client.renderer.OthoPetEntityRenderer;
import com.virtualeria.eriapets.client.renderer.PinguPetEntityRenderer;
import com.virtualeria.eriapets.client.renderer.SlimerPetEntityRenderer;
import com.virtualeria.eriapets.entities.EntityRegistryPets;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Environment(EnvType.CLIENT)
public class EriaPetsMainClient  implements ClientModInitializer {

    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onInitializeClient() {
        LOGGER.info("[ERIAPETS CLIENT] Inizialitze client");

        ScreenRegistry.<PetGuiDescription, CottonInventoryScreen<PetGuiDescription>>register(
                EriaPetsMain.PET_SCREEN_HANDLER_TYPE,
                (desc, inventory, title) -> new PetScreen(desc, inventory.player, title)
        );

        EntityRendererRegistry.INSTANCE.register(EntityRegistryPets.BASE_PET_ENTITY, BasePetEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistryPets.OTHO_PET_ENTITY, OthoPetEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistryPets.SLIMER_PET_ENTITY, SlimerPetEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistryPets.PINGU_PET_ENTITY, PinguPetEntityRenderer::new);
    }
}
