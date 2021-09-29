package com.virtualeria.eriapets.entities;


import com.virtualeria.eriapets.utils.NetworkHelper;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class UsagiPetEntity extends BasePetEntity {

    public static final String petName = "usagi";

    public UsagiPetEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public static final float SMASH_RADIO = 2;

    public void smashGroundPlayer(BlockPos pos) {
        float radio = SMASH_RADIO;
        List<Entity> listEntities = world.getOtherEntities(this, new Box(pos.getX() - radio, pos.getY() - radio, pos.getZ() - radio,
                pos.getX() + radio, pos.getY() + radio, pos.getZ() + radio));

        for (Entity entity : listEntities) {
            Vec3d velocity = entity.getVelocity().add(new Vec3d(0, 1, 0));
            entity.setVelocity(velocity);
            entity.velocityDirty = true;
        }

        if (world.isClient) {
            drawParticlesSmash(pos, world);
        } else {
            sendDrawParticlesToClients(pos);
            playSmashGroundSound(pos);
        }


    }

    public void sendDrawParticlesToClients(BlockPos target) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(target);
        for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, target)) {
            ServerPlayNetworking.send((ServerPlayerEntity) player, NetworkHelper.DRAW_PARTICLES_USAGI, buf);
        }
    }

    public void playSmashGroundSound(BlockPos pos) {
        world.playSound(
                null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                pos, // The position of where the sound will come from
                SoundEvents.ENTITY_WITHER_BREAK_BLOCK, // The sound that will play
                SoundCategory.BLOCKS, // This determines which of the volume sliders affect this sound
                1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
        );

    }

    public static void drawParticlesSmash(BlockPos pos, World world) {
        for (float z = pos.getZ() + SMASH_RADIO; z > pos.getZ() - SMASH_RADIO; z--) {
            for (float x = (pos.getX() + SMASH_RADIO); x > pos.getX() - SMASH_RADIO; x--) {
                for (float y = (pos.getY() + SMASH_RADIO); y > pos.getY() - SMASH_RADIO; y--) {
                    BlockState blockstate = world.getBlockState(new BlockPos(x, y, z));
                    BlockState upperBlock = world.getBlockState(new BlockPos(x, y + 1, z));
                    if (!blockstate.isAir() && upperBlock.isAir())
                        MinecraftClient.getInstance().particleManager.addParticle(
                                new BlockStateParticleEffect(ParticleTypes.BLOCK, blockstate), x, y + 1.2, z,
                                0.0D, 0, 0.0D
                        );
                }
            }
        }
    }

}
