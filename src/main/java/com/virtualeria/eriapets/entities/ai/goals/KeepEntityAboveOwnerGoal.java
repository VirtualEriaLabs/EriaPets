package com.virtualeria.eriapets.entities.ai.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.TameableEntity;

public class KeepEntityAboveOwnerGoal extends Goal {

    TameableEntity entity;
    public KeepEntityAboveOwnerGoal(TameableEntity entity){
        this.entity = entity;
    }

    @Override
    public boolean canStart() {
        if(this.entity.getOwner() != null
                &&  this.entity.getY() < this.entity.getOwner().getY() + 1
                && this.entity.getNavigation().isIdle())
            return true;

        return false;
    }

    public void tick() {
        this.entity.addVelocity(0,0.05,0);
        this.entity.velocityDirty = true;
    }

    public boolean shouldContinue() {
        if(this.entity.getOwner() != null
                &&  this.entity.getY() < this.entity.getOwner().getY() + 1
                && this.entity.getNavigation().isIdle())
            return true;

        return false;
    }
}
