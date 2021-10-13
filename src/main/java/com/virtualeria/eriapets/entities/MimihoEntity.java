package com.virtualeria.eriapets.entities;


import com.virtualeria.eriapets.entities.AnimationController.MimihoAnimationController;
import com.virtualeria.eriapets.entities.ai.goals.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.TameableEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;


public class MimihoEntity extends SpumaEntity {

    public static final String petName = "mimiho";
    private static final TrackedData<Boolean> isPlayerInside;
    boolean abilityStaticPose = false;
    MimihoAnimationController animationController;

    static {
        isPlayerInside = DataTracker.registerData(MimihoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public MimihoEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.inanimate = true;
        animationController = new MimihoAnimationController(this);

    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    public boolean isPushable() {
        return false;
    }

    protected void pushAway(Entity entity) {
    }

    /**
     * Initialize sync data
     */
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(isPlayerInside, false);
    }

    public void drawPetEffects(World world) {

    }

    @Override
    public boolean collides() {
        return !isPlayerInside();
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController controller = new AnimationController(this, "controller", 0.0F, animationController::predicateMimiho);
        data.addAnimationController(controller);
    }

    @Override
    public void initGoals() {
        this.goalSelector.add(1, new SlimeSwimmingGoal(this));
        this.goalSelector.add(2, new MimihoEatPlayer(this, 150));
        this.goalSelector.add(3, new SlimeFaceTowardTargetGoal(this));
        this.goalSelector.add(5, new PetLookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(4, new PetFollowOwnerGoal(this, 1f, 3f, 8, false));
    }

    @Override
    public void customAbility() {
        this.setAbilityRunning(true);
    }

    public boolean isPlayerInside() {
        return this.dataTracker.get(this.isPlayerInside);
    }

    public void setPlayerIsInside(boolean v) {
        this.dataTracker.set(this.isPlayerInside, v);
    }
}
