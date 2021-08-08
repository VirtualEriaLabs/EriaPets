package com.virtualeria.eriapets.entities;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;


public class SlimerPetEntity extends BasePetEntity {
    public static final String petName = "slimer";

    public SlimerPetEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void customAbility() {
        //Don't do anything passive ability
    }
}
