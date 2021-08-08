package com.virtualeria.eriapets.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;

public class PinguPetEntity extends BasePetEntity{
    public static final String petName = "pingu";
    public PinguPetEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }
    @Override
    public void customAbility() {
        //Don't do anything passive ability
    }
}
