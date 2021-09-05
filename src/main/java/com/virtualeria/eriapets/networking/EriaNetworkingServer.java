package com.virtualeria.eriapets.networking;


import com.virtualeria.eriapets.access.PlayerEntityDuck;
import com.virtualeria.eriapets.entities.BasePetEntity;
import com.virtualeria.eriapets.utils.NetworkHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;


public class EriaNetworkingServer {

    public static void registerServerListener() {
        ServerPlayNetworking.registerGlobalReceiver(NetworkHelper.PET_ABILITY_TRIGGER, (server, player,  handler,  buf,  responseSender) -> {
            // Read packet data on the event loop
            server.execute(() -> {
                // Everything in this lambda is run on the render thread
                Entity ownedEntity = player.world.getEntityById(((PlayerEntityDuck) player).getOwnedPetID());
                if (ownedEntity instanceof BasePetEntity) {
                    BasePetEntity basePet = (BasePetEntity) ownedEntity;
                    if(basePet.canUseAbility())
                    basePet.customAbility();
                }
            });
        });
    }
}
