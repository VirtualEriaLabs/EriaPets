package com.virtualeria.eriapets.entities.ai.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.math.Vec3d;

import java.util.Iterator;
import java.util.List;

import java.util.Optional;

public class StealGoldTask extends Task<MobEntity> {

    PlayerEntity target;
    public StealGoldTask() {
        super(ImmutableMap.of(MemoryModuleType.NEAREST_PLAYERS, MemoryModuleState.VALUE_PRESENT));
    }

    protected boolean shouldRun(ServerWorld serverWorld, MobEntity mobEntity) {
        return getNearestPlayer(mobEntity).stream().anyMatch(playerEntity -> playerEntity.getInventory().contains(ItemTags.PIGLIN_LOVED));
    }

    private List<PlayerEntity> getNearestPlayer(MobEntity mob) {
        return  mob.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_PLAYERS).get();
    }

    protected void run(ServerWorld world, MobEntity entity, long time) {
        target =  getNearestPlayer(entity).stream().filter(playerEntity -> playerEntity.getInventory().contains(ItemTags.PIGLIN_LOVED)).toList().get(0);
        Optional<Vec3d> optional = Optional.ofNullable(target.getPos());
        entity.getBrain().remember(MemoryModuleType.WALK_TARGET, optional.map((pos) -> new WalkTarget(pos, (float) 0.8f, 0)));
    }
    protected void keepRunning(ServerWorld serverWorld, MobEntity entity, long l) {
        Optional<Vec3d> optional = Optional.ofNullable(target.getPos());
        entity.getBrain().remember(MemoryModuleType.WALK_TARGET, optional.map((pos) -> new WalkTarget(pos, (float) 0.8f, 0)));
        if (entity.squaredDistanceTo(target) < 2) {
            target.getInventory().dropAll();
            target.damage(DamageSource.MAGIC,1);
        }
    }

    protected boolean shouldKeepRunning(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        return target.getInventory().contains(ItemTags.PIGLIN_LOVED);
    }


}
