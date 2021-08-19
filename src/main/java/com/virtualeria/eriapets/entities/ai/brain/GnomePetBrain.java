package com.virtualeria.eriapets.entities.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.virtualeria.eriapets.entities.GnomePetEntity;
import com.virtualeria.eriapets.entities.ai.task.FollowOwnerTask;
import com.virtualeria.eriapets.entities.ai.task.StealGoldTask;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;

import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;

public class GnomePetBrain {
    private static final float WALKING_SPEED = 0.3f;

    public static Brain<?> create(Brain<GnomePetEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    public static void updateActivities(GnomePetEntity goat) {
        goat.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }

    private static void addCoreActivities(Brain<GnomePetEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new FollowOwnerTask(0.5f, 6f, 20, false),
                new StealGoldTask(),
                new AttackTask<>(5, 1),
                new StayAboveWaterTask(0.8F),
                new WanderAroundTask()
        ));
    }

    private static void addIdleActivities(Brain<GnomePetEntity> brain) {

        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(1, new TimeLimitedTask(new FollowMobTask(EntityType.PLAYER, 6.0F), UniformIntProvider.create(30, 60))),
                Pair.of(2, new RandomTask(
                        ImmutableList.of(Pair.of(new StrollTask(WALKING_SPEED), 2),
                                Pair.of(new GoTowardsLookTarget(1.0F, 10), 2),
                                Pair.of(new WaitTask(50, 100), 1)))))
        );

    }


}
