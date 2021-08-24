package com.virtualeria.eriapets.utils;

import com.virtualeria.eriapets.access.PlayerEntityDuck;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;


public class PetUtils {

    public static Entity getPlayerOwnedPet( PlayerEntity player){
      return player.world.getEntityById(((PlayerEntityDuck) player).getOwnedPetID());
    }

}
