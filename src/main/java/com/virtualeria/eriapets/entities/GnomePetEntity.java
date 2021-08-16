package com.virtualeria.eriapets.entities;


import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import com.virtualeria.eriapets.entities.ai.brain.GnomePetBrain;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AxolotlBrain;
import net.minecraft.entity.passive.GoatBrain;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.Optional;

public class GnomePetEntity extends BasePetEntity {
    public static String petName = "gnome";

    protected static final ImmutableList<SensorType<? extends Sensor<? super GnomePetEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    static {
        SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES,
                SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS,
                SensorType.HURT_BY
               );

        MEMORY_MODULES = ImmutableList.of(
                MemoryModuleType.LOOK_TARGET, MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH
                );
    }

    protected Brain.Profile<GnomePetEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return GnomePetBrain.create(this.createBrainProfile().deserialize(dynamic));
    }
    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).add(EntityAttributes.GENERIC_ATTACK_DAMAGE,1);
    }
    public Brain<GnomePetEntity> getBrain() {
        return (Brain<GnomePetEntity>) super.getBrain();
    }
    @Override
    protected void initGoals() {

    }
    @Override
    public void mobTick() {
        if(getCustomDeath() == 0)tickBrain();
        super.mobTick();
    }
    public void tickBrain(){
        this.world.getProfiler().push("GnomePetBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().pop();
        GnomePetBrain.updateActivities(this);
    }
    public GnomePetEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }
}
