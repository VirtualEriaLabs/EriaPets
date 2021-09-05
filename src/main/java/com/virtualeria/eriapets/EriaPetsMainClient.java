package com.virtualeria.eriapets;

import com.virtualeria.eriapets.client.KeyBindings;
import com.virtualeria.eriapets.client.renderer.*;
import com.virtualeria.eriapets.client.gui.PetGuiDescription;
import com.virtualeria.eriapets.client.gui.PetScreen;
import com.virtualeria.eriapets.client.renderer.BasePetEntityRenderer;

import com.virtualeria.eriapets.client.renderer.OthoPetEntityRenderer;
import com.virtualeria.eriapets.client.renderer.PinguPetEntityRenderer;
import com.virtualeria.eriapets.client.renderer.SlimerPetEntityRenderer;
import com.virtualeria.eriapets.entities.EntityRegistryPets;
import com.virtualeria.eriapets.entities.PinguPetEntity;
import com.virtualeria.eriapets.entities.SlimerPetEntity;
import com.virtualeria.eriapets.networking.EriaNetworkingClient;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;


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
