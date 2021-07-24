package com.virtualeria.eriapets.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class OthoPetEntity extends  BasePetEntity{
    public OthoPetEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public void playAbilitySound(){
        if (!world.isClient) {
            world.playSound(
                    null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                    new BlockPos(new Vec3d(getX(),getY(),getZ())), // The position of where the sound will come from
                    SoundEvents.BLOCK_ANVIL_LAND, // The sound that will play, in this case, the sound the anvil plays when it lands.
                    SoundCategory.BLOCKS, // This determines which of the volume sliders affect this sound
                    1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                    1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
            );
        }
    }
    @Override
    public void customAbility(){
        //Don't do anything passive ability
    }

}
