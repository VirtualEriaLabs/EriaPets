package com.virtualeria.eriapets.entities.ai.goals;

import com.virtualeria.eriapets.entities.BasePetEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.mob.MobEntity;

public class PetLookAtEntityGoal extends LookAtEntityGoal {
    public PetLookAtEntityGoal(MobEntity mob, Class<? extends LivingEntity> targetType, float range) {
        super(mob, targetType, range);
    }

    public boolean canStart() {
        if (!isPetAlive()) return false;
        return super.canStart();
    }

    public boolean shouldContinue() {
        if (!isPetAlive()) return false;
        return super.shouldContinue();
    }

    public boolean isPetAlive() {
        return (this.mob instanceof BasePetEntity && ((BasePetEntity) this.mob).isAlive());
    }
}
