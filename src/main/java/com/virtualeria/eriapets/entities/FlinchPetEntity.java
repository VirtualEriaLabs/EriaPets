package com.virtualeria.eriapets.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;

public class FlinchPetEntity extends BasePetEntity {
    public static final String petName = "flinch";

    public FlinchPetEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }


    public void canPoisonTarget(Entity target) {
        if (abilityIsCooledDown()) {
            useAbility();
            if (target instanceof LivingEntity)
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 1500));

            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 1500));

        }
    }
}
