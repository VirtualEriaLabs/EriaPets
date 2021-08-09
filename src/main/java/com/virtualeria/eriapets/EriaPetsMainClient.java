package com.virtualeria.eriapets;

import com.virtualeria.eriapets.client.renderer.*;

import com.virtualeria.eriapets.entities.EntityRegistryPets;
import com.virtualeria.eriapets.entities.PinguPetEntity;
import com.virtualeria.eriapets.entities.SlimerPetEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Environment(EnvType.CLIENT)
public class EriaPetsMainClient  implements ClientModInitializer {

    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onInitializeClient() {
        LOGGER.info("[ERIAPETS CLIENT] Inizialitze client");
        RegisterRenderers.onIntialize();

    }
}
