package com.virtualeria.eriapets.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;

public class PinguPetEntity extends BasePetEntity{
    public PinguPetEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }
}
