package com.virtualeria.eriapets;


import com.virtualeria.eriapets.entities.EntityRegistryPets;
import com.virtualeria.eriapets.events.EventsRegistry;
import com.virtualeria.eriapets.networking.EriaNetworkingServer;
import net.fabricmc.api.ModInitializer;

import com.virtualeria.eriapets.client.gui.PetGuiDescription;
import com.virtualeria.eriapets.entities.BasePetEntity;
import com.virtualeria.eriapets.entities.EntityRegistryPets;
import com.virtualeria.eriapets.events.EventsRegistry;
import com.virtualeria.eriapets.utils.Constants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;


import java.util.Optional;


public class EriaPetsMain implements ModInitializer {

    public static ScreenHandlerType<PetGuiDescription> PET_SCREEN_HANDLER_TYPE;
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {

        PET_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerExtended(new Identifier(Constants.ModID,"pet_entity_gui"), (syncId, inventory, buf) -> {
            BlockPos pos = buf.readBlockPos();
            int entityId = buf.readInt();
            BasePetEntity petEntity;
            Optional<Entity> optional = Optional.ofNullable(inventory.player.world.getEntityById(entityId));
            if (optional.isPresent()) {
                petEntity = (BasePetEntity) optional.get();
            } else
                throw new AssertionError("Failed to get PetEntity in GUI for: " + entityId + " and player: " + inventory.player.getDisplayName().asString());
            return new PetGuiDescription(syncId, inventory, ScreenHandlerContext.create(inventory.player.world, pos), petEntity);
        });

        LOGGER.info("[EriaPets] Initialize");
        GeckoLib.initialize();
        EntityRegistryPets.initialize();
        EventsRegistry.registerEvents();
        EriaNetworkingServer.registerServerListener();
    }
}
