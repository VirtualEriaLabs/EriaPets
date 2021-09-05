package com.virtualeria.eriapets.entities.ai.task;

import com.virtualeria.eriapets.entities.BasePetEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class FlyWanderAround extends Goal {
    BasePetEntity entity;
    public FlyWanderAround(BasePetEntity entity) {
        this.setControls(EnumSet.of(Goal.Control.MOVE));
        this.entity = entity;
    }

    public boolean canStart() {
        return this.entity.getNavigation().isIdle() && this.entity.getRandom().nextInt(120) == 0;
    }

    public boolean shouldContinue() {
        return this.entity.getNavigation().isFollowingPath();
    }

    public void start() {

        Vec3d vec3d = this.getRandomLocation();
        if (vec3d != null) {
            this.entity.getNavigation().startMovingAlong(this.entity.getNavigation().findPathTo((BlockPos)(new BlockPos(vec3d)), 2), 1);
        }
    }

    @Nullable
    private Vec3d getRandomLocation() {
        Vec3d vec3d3;
        vec3d3 = this.entity.getRotationVec(0.0F);
        int i = 0;
        Vec3d vec3d4 = AboveGroundTargeting.find(this.entity, 8, 7, vec3d3.x, vec3d3.z, 1.5707964F, 2, 1);
        return vec3d4 != null ? vec3d4 : NoPenaltySolidTargeting.find(this.entity, 8, 4, -2, vec3d3.x, vec3d3.z, 1.5707963705062866D);
    }
}