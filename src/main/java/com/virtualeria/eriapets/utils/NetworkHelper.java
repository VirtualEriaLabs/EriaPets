package com.virtualeria.eriapets.utils;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;


public class NetworkHelper {
    public static final Identifier DRAW_PARTICLES_USAGI = new Identifier(Constants.ModID, "particlepet");
    public static final Identifier PET_ABILITY_TRIGGER = new Identifier(Constants.ModID, "petability");
    public static final Identifier SYNC_EFFECT_SPUMAPET = new Identifier(Constants.ModID, "spumapeteffect");
    public static final Identifier SYNC_PET_EFFECTS = new Identifier(Constants.ModID, "peteffects");
    public static final Identifier SYNC_PET_SPARKY = new Identifier(Constants.ModID, "peteffectssparky");

    public static void sendPacketToAllNerbyClients(ServerWorld world, BlockPos pos, Identifier packetIdentifier, PacketByteBuf buf) {
        for (ServerPlayerEntity player : PlayerLookup.tracking(world, pos)) {
            ServerPlayNetworking.send((ServerPlayerEntity) player, packetIdentifier, buf);
        }
    }
}
