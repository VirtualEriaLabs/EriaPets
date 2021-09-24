package com.virtualeria.eriapets.entities.ai.goals;

import com.virtualeria.eriapets.entities.SpumaEntity;
import com.virtualeria.eriapets.entities.ai.MoveControls.SlimeMoveControl;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class SlimeFaceTowardTargetGoal extends Goal {
    private final SpumaEntity slime;
    private int ticksLeft;

    public SlimeFaceTowardTargetGoal(SpumaEntity slime) {
        this.slime = slime;
        this.setControls(EnumSet.of(Goal.Control.LOOK));
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.slime.getOwner();
        if (livingEntity == null) {
            return false;
        } else if (!slime.isAlive()) {
            return false;
        } else if (slime.isAbilityRunning()) {
            return true;
        }
        return true;
    }

    public void start() {
        this.ticksLeft = 300;
        super.start();
    }

    public boolean shouldContinue() {
        LivingEntity livingEntity = this.slime.getOwner();
        if (!slime.isAlive()) {
            return false;
        } else if (livingEntity != null) {
            return true;
        } else if (slime.isAbilityRunning()) {
            return true;
        }
        return false;
    }

    public void tick() {
        if (this.slime.getNavigation().getTargetPos() != null && slime.isAbilityRunning()) {
            this.slime.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, new Vec3d(this.slime.getTargetExplosionPos().getX(), this.slime.getTargetExplosionPos().getY(), this.slime.getTargetExplosionPos().getZ()));
        } else this.slime.lookAtEntity(this.slime.getOwner(), 10.0F, 10.0F);

        ((SlimeMoveControl) this.slime.getMoveControl()).look(this.slime.getYaw(), true);
    }

}
