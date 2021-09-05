package com.virtualeria.eriapets;

import com.virtualeria.eriapets.client.KeyBindings;
import com.virtualeria.eriapets.client.renderer.*;
import com.virtualeria.eriapets.client.gui.PetGuiDescription;
import com.virtualeria.eriapets.client.gui.PetScreen;
import com.virtualeria.eriapets.networking.EriaNetworkingClient;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@Environment(EnvType.CLIENT)
public class EriaPetsMainClient  implements ClientModInitializer {

    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onInitializeClient() {
        LOGGER.info("[ERIAPETS CLIENT] Inizialitze client");
        RegisterRenderers.onIntialize();
        KeyBindings.registerKeyBindings();
        EriaNetworkingClient.registerClientListener();

        ScreenRegistry.<PetGuiDescription, CottonInventoryScreen<PetGuiDescription>>register(
                EriaPetsMain.PET_SCREEN_HANDLER_TYPE,
                (desc, inventory, title) -> new PetScreen(desc, inventory.player, title)
        );


    }
}
