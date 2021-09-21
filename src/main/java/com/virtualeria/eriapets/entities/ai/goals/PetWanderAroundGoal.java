package com.virtualeria.eriapets.entities.ai.goals;

import com.virtualeria.eriapets.entities.BasePetEntity;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class PetWanderAroundGoal  extends WanderAroundGoal {
    public PetWanderAroundGoal(PathAwareEntity mob, double speed) {
        super(mob, speed);
    }

    public boolean canStart() {
        if(isBasePetDead()) return false;

        return  super.canStart();
    }

    public boolean shouldContinue() {
        if(isBasePetDead()) return false;
        return  super.shouldContinue();
    }

    public boolean isBasePetDead(){
        return (this.mob instanceof BasePetEntity && ((BasePetEntity) this.mob).getCustomDeath() > 1);
    }
}
