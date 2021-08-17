package com.virtualeria.eriapets.entities.ai.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;

import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;

import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class FollowOwnerTask extends Task<TameableEntity> {

    private static final int HORIZONTAL_RANGE = 2;
    private static final int HORIZONTAL_VARIATION = 3;
    private static final int VERTICAL_VARIATION = 1;

    private LivingEntity owner;

    private final double speed;


    private final float maxDistance;
    private final float minDistance;

    private final boolean leavesAllowed;


    public FollowOwnerTask(double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
        super(ImmutableMap.of(), 30, 200);
        this.speed = speed;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.leavesAllowed = leavesAllowed;

    }

    protected boolean shouldRun(ServerWorld serverWorld, TameableEntity mobEntity) {

        LivingEntity livingEntity = mobEntity.getOwner();
        if (livingEntity != null && mobEntity.getOwner() != null)
            System.out.println("DISTANCE " + mobEntity.squaredDistanceTo(livingEntity) + " (double)  " + (double) (this.minDistance * this.minDistance));
        if (livingEntity == null) {
            return false;
        } else if (livingEntity.isSpectator()) {
            return false;
        } else if (mobEntity.isSitting()) {
            return false;
        } else if (mobEntity.squaredDistanceTo(livingEntity) < (double) (this.minDistance * this.minDistance)) {
            return false;

        } else {
            this.owner = livingEntity;
            return true;
        }
    }


    protected void run(ServerWorld world, TameableEntity entity, long time) {

        Optional<Vec3d> optional = Optional.ofNullable(entity.getOwner().getPos());
        entity.getBrain().remember(MemoryModuleType.WALK_TARGET, optional.map((pos) -> {
            System.out.println("WALK TARGET " + pos);
            return new WalkTarget(pos, (float) this.speed, 0);
        }));


    }

    protected boolean shouldKeepRunning(ServerWorld serverWorld, TameableEntity mobEntity, long l) {
        if (mobEntity.getNavigation().isIdle()) {
            return false;
        } else if (mobEntity.isSitting()) {
            return false;
        } else {
            return !(mobEntity.squaredDistanceTo(mobEntity.getOwner()) <= (double) (this.maxDistance * this.maxDistance));
        }

    }

    protected void keepRunning(ServerWorld serverWorld, TameableEntity entity, long l) {
        entity.getLookControl().lookAt(this.owner, 10.0F, (float) entity.getLookPitchSpeed());
        if (!entity.isLeashed() && !entity.hasVehicle()) {
            if (entity.squaredDistanceTo(this.owner) >= 144.0D) {
                 this.tryTeleport(entity);
            } else {
                Optional<Vec3d> optional = Optional.ofNullable(entity.getOwner().getPos());
                entity.getBrain().remember(MemoryModuleType.WALK_TARGET, optional.map((pos) -> {
                    System.out.println("WALK TARGET " + pos);
                    return new WalkTarget(pos, (float) this.speed, 0);
                }));
            }
        }
    }
    private void tryTeleport(TameableEntity entity) {
        BlockPos blockPos = this.owner.getBlockPos();

        for(int i = 0; i < 10; ++i) {
            int j = this.getRandomInt(-3, 3,entity);
            int k = this.getRandomInt(-1, 1,entity);
            int l = this.getRandomInt(-3, 3,entity);
            boolean bl = this.tryTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l,entity);
            if (bl) {
                return;
            }
        }

    }

    private boolean tryTeleportTo(int x, int y, int z,TameableEntity tameable) {
        if (Math.abs((double)x - this.owner.getX()) < 2.0D && Math.abs((double)z - this.owner.getZ()) < 2.0D) {
            return false;
        } else if (!this.canTeleportTo(new BlockPos(x, y, z),tameable)) {
            return false;
        } else {
            tameable.refreshPositionAndAngles((double)x + 0.5D, (double)y, (double)z + 0.5D, tameable.getYaw(), tameable.getPitch());
            tameable.getNavigation().stop();
            return true;
        }
    }

    private boolean canTeleportTo(BlockPos pos,TameableEntity tameable) {
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(tameable.world, pos.mutableCopy());
        if (pathNodeType != PathNodeType.WALKABLE) {
            return false;
        } else {
            BlockState blockState = tameable.world.getBlockState(pos.down());
            if (!this.leavesAllowed && blockState.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockPos = pos.subtract(tameable.getBlockPos());
                return tameable.world.isSpaceEmpty(tameable, tameable.getBoundingBox().offset(blockPos));
            }
        }
    }

    private int getRandomInt(int min, int max,TameableEntity entity) {
        return entity.getRandom().nextInt(max - min + 1) + min;
    }

}
