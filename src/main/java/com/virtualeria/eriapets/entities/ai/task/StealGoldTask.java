package com.virtualeria.eriapets.entities.ai.task;

import com.google.common.collect.ImmutableMap;
import com.virtualeria.eriapets.access.PlayerInventoryDuck;
import com.virtualeria.eriapets.entities.BasePetEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.math.Vec3d;

import java.util.List;

import java.util.Optional;

public class StealGoldTask extends Task<MobEntity> {

    PlayerEntity target;
    ItemStack stealedItem;
    Vec3d startPos;

    public StealGoldTask() {
        super(ImmutableMap.of(MemoryModuleType.NEAREST_PLAYERS, MemoryModuleState.VALUE_PRESENT));
    }

    protected boolean shouldRun(ServerWorld serverWorld, MobEntity mobEntity) {
        return (getNearestPlayer(mobEntity).stream().anyMatch(playerEntity -> playerEntity.getInventory().contains(ItemTags.PIGLIN_LOVED)) &&
                ((BasePetEntity) mobEntity).abilityIsCooledDown()) || stealedItem != null;
    }

    private List<PlayerEntity> getNearestPlayer(MobEntity mob) {
        return mob.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_PLAYERS).get();
    }

    protected void run(ServerWorld world, MobEntity entity, long time) {
        List<PlayerEntity> playerList = getNearestPlayer(entity).stream().filter(playerEntity -> playerEntity.getInventory().contains(ItemTags.PIGLIN_LOVED)).toList();
        if(playerList.size() > 0){
            this.target = playerList.get(0);
            moveTo(this.target.getPos(), entity);
            startPos = entity.getPos();
        }
    }

    protected void keepRunning(ServerWorld serverWorld, MobEntity entity, long l) {
        moveTo(target.getPos(), entity);
        if (entity.squaredDistanceTo(target) < 2 && stealedItem == null) {
            stealItem();
        } else if (this.stealedItem != null) {
            if (((TameableEntity) entity).getOwner() != null) {
                returnToOwner(entity);
                Vec3d ownerPos = ((TameableEntity) entity).getOwner().getPos();
                if (entity.squaredDistanceTo(ownerPos) < 2)
                    dropStolenItem(entity, ownerPos);
            } else {
                hideItem(entity);
                if (entity.squaredDistanceTo(this.startPos) < 2)
                    dropStolenItem(entity, startPos);
            }
        }
    }


    private void stealItem() {
        ItemStack item = ((PlayerInventoryDuck) this.target.getInventory()).getFirstTagItemSlotId(ItemTags.PIGLIN_LOVED);
        this.target.damage(DamageSource.MAGIC, 1);
        this.stealedItem = item;
        this.target.getInventory().removeOne(item);
    }

    private void dropStolenItem(MobEntity entity, Vec3d pos) {
        ((BasePetEntity) entity).useAbility();
        this.stealedItem.setHolder(null);
        entity.getEntityWorld().spawnEntity(new ItemEntity(entity.getEntityWorld(), pos.getX(), pos.getY(), pos.getZ(), this.stealedItem));
        this.stealedItem = null;
        this.startPos = null;
        this.target = null;
    }

    private void hideItem(MobEntity entity) {
        moveTo(this.startPos, entity);
    }

    private void returnToOwner(MobEntity entity) {
        LivingEntity owner = ((TameableEntity) entity).getOwner();
        moveTo(owner.getPos(), entity);
        this.stealedItem = null;
    }

    private void moveTo(Vec3d position, MobEntity entity) {
        Optional<Vec3d> optional = Optional.ofNullable(position);
        entity.getBrain().remember(MemoryModuleType.WALK_TARGET, optional.map((pos) -> new WalkTarget(pos, (float) 0.8f, 0)));
    }

    protected boolean shouldKeepRunning(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        return this.target != null && (target.getInventory().contains(ItemTags.PIGLIN_LOVED) || this.stealedItem != null);
    }


}
