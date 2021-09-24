package com.virtualeria.eriapets.networking;


import com.virtualeria.eriapets.access.PlayerEntityDuck;
import com.virtualeria.eriapets.entities.BasePetEntity;
import com.virtualeria.eriapets.utils.NetworkHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EriaNetworkingServer {

    private static final Logger LOGGER = LogManager.getLogger();
    public static void registerServerListener() {
        ServerPlayNetworking.registerGlobalReceiver(NetworkHelper.PET_ABILITY_TRIGGER, (server, player,  handler,  buf,  responseSender) -> {
            // Read packet data on the event loop
            server.execute(() -> {
                // Everything in this lambda is run on the render thread
                LOGGER.info("[EriaPETS] SERVER PET ABILITY TRIGGER received");
                Entity ownedEntity = player.world.getEntityById(((PlayerEntityDuck) player).getOwnedPetID());
                if (ownedEntity instanceof BasePetEntity) {
                    LOGGER.info("[EriaPETS] SERVER PET ABILITY TRIGGER has pet");
                    BasePetEntity basePet = (BasePetEntity) ownedEntity;
                    if(basePet.canUseAbility())
                    basePet.customAbility();
                }
            });
        });
    }
}
