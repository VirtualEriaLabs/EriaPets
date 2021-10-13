package com.virtualeria.eriapets.networking;

import com.virtualeria.eriapets.entities.BasePetEntity;
import com.virtualeria.eriapets.entities.SparkyEntity;
import com.virtualeria.eriapets.entities.SpumaEntity;
import com.virtualeria.eriapets.entities.UsagiPetEntity;

import com.virtualeria.eriapets.utils.NetworkHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public class EriaNetworkingClient {


    public static void registerClientListener() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkHelper.DRAW_PARTICLES_USAGI, (client, handler, buf, responseSender) -> {
            // Read packet data on the event loop
            BlockPos target = buf.readBlockPos();
            client.execute(() -> {
                // Everything in this lambda is run on the render thread
                UsagiPetEntity.drawParticlesSmash(target, MinecraftClient.getInstance().world);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(NetworkHelper.SYNC_EFFECT_SPUMAPET, (client, handler, buf, responseSender) -> {
            // Read packet data on the event loop
            int spumaEntityId = buf.readInt();
            client.execute(() -> {
                // Everything in this lambda is run on the render thread
                SpumaEntity spumaEntity = (SpumaEntity) MinecraftClient.getInstance().world.getEntityById(spumaEntityId);
                spumaEntity.playAbilityAnim(MinecraftClient.getInstance().world);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(NetworkHelper.SYNC_PET_EFFECTS, (client, handler, buf, responseSender) -> {
            // Read packet data on the event loop
            int basePetId = buf.readInt();
            client.execute(() -> {
                // Everything in this lambda is run on the render thread
                BasePetEntity basepet = (BasePetEntity) MinecraftClient.getInstance().world.getEntityById(basePetId);
                basepet.drawPetEffects(MinecraftClient.getInstance().world);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(NetworkHelper.SYNC_PET_SPARKY, (client, handler, buf, responseSender) -> {
            // Read packet data on the event loop
            int basePetId = buf.readInt();
            BlockPos pos = buf.readBlockPos();
            client.execute(() -> {
                // Everything in this lambda is run on the render thread
                SparkyEntity basepet = (SparkyEntity) MinecraftClient.getInstance().world.getEntityById(basePetId);
                basepet.drawPetEffects(MinecraftClient.getInstance().world,pos);
            });
        });

    }
}
