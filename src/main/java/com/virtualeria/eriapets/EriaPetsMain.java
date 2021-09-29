package com.virtualeria.eriapets;


import com.virtualeria.eriapets.Sounds.SoundsRegistry;
import com.virtualeria.eriapets.client.gui.ScreenHandlerRegister;
import com.virtualeria.eriapets.entities.EntityRegistryPets;
import com.virtualeria.eriapets.events.EventsRegistry;
import com.virtualeria.eriapets.networking.EriaNetworkingServer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;


public class EriaPetsMain implements ModInitializer {


    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info("[EriaPets] Initialize start");
        GeckoLib.initialize();
        EntityRegistryPets.initialize();
        EventsRegistry.registerEvents();
        SoundsRegistry.initialize();
        EriaNetworkingServer.registerServerListener();
        ScreenHandlerRegister.initialize();
        LOGGER.info("[EriaPets] Initialized done");
    }
}
