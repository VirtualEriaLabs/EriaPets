package com.virtualeria.eriapets.entities.ai.goals;

import com.virtualeria.eriapets.entities.SpumaEntity;
import com.virtualeria.eriapets.utils.NetworkHelper;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;


import java.util.EnumSet;
import java.util.List;

public class SpumaPetExplosion extends Goal {

    SpumaEntity spumaEntity;
    ServerWorld world;
    int explosionDuration = 27;
    int maxGoalDuration = 200;
    int duration = 0;
    int currentExplosionDuration = 0;
    boolean reachedDestionation = false;
    private float oldWaterPathfindingPenalty;

    public SpumaPetExplosion(SpumaEntity spumaEntity, ServerWorld world) {
        this.spumaEntity = spumaEntity;
        this.world = world;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (!this.spumaEntity.isAlive()) {
            return false;
        }
        if(!this.spumaEntity.canUseAbility())
            return false;
        if (this.spumaEntity.getTargetExplosionPos() != null) {
            return true;
        }

        return false;
    }

    public void start() {
        System.out.println("START");
        moveToTargetExplosion();
        this.oldWaterPathfindingPenalty = this.spumaEntity.getPathfindingPenalty(PathNodeType.WATER);
        this.spumaEntity.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.spumaEntity.setPathfindingPenalty(PathNodeType.LAVA, 10F);

    }

    public void stop() {
        currentExplosionDuration = 0;
        reachedDestionation = false;
        this.spumaEntity.setAbilityRunning(false);
        duration = 0;
        this.spumaEntity.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
    }

    public void tick() {
        if (!reachedDestionation && duration < maxGoalDuration) {
            getToTargetBlock();
        } else {
            triggerExplosionEffects();
            triggerExplosion();
        }
        duration++;
    }


    public void triggerExplosion() {
        currentExplosionDuration++;
        switch (currentExplosionDuration) {
            case 27:
                System.out.println("EXPLOSION TRIGGERED");
                explosion();
                break;
            case 44:
                System.out.println("FINISHED TRIGGERED");
                this.spumaEntity.setTargetExplosionPos(null);
                this.spumaEntity.setAbilityRunning(false);
                this.spumaEntity.useAbility();
                break;
        }
    }


    void explosion() {
        float radio = 5;
        List<Entity> listEntities = world.getOtherEntities(this.spumaEntity, new Box(this.spumaEntity.getX() - radio, this.spumaEntity.getY() - radio, this.spumaEntity.getZ() - radio,
                this.spumaEntity.getX() + radio, this.spumaEntity.getY() + radio, this.spumaEntity.getZ() + radio));
        double x = 0;
        double y = 0;
        for (Entity entity : listEntities) {
            entity.damage(DamageSource.MAGIC, 3);
            x = Math.cos(entity.getX()) * radio;
            y = Math.sin(entity.getZ()) * radio;
            MinecraftClient.getInstance().particleManager.addParticle(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE, this.spumaEntity.getX(), this.spumaEntity.getY(), this.spumaEntity.getZ(),
                    x * 0.05f, 0.0D, y * 0.05f
            );

            Vec3d a = this.spumaEntity.getPos().subtract(entity.getPos()).normalize();


            entity.addVelocity(-a.getX(), 1, -a.getZ());
            entity.velocityDirty = true;
        }
    }

    public void getToTargetBlock() {
        if (this.spumaEntity.squaredDistanceTo(this.spumaEntity.getTargetExplosionPos().getX(),
                this.spumaEntity.getTargetExplosionPos().getY(),
                this.spumaEntity.getTargetExplosionPos().getZ()) < 3.5) {
            System.out.println("ARRIVED");
            reachedDestionation = true;

        } else {
            moveToTargetExplosion();
        }
    }

    void triggerExplosionEffects() {
        //Trigger the explosion effects
        for (ServerPlayerEntity player : PlayerLookup.tracking(this.world, this.spumaEntity.getTargetExplosionPos())) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(this.spumaEntity.getId());
            ServerPlayNetworking.send((ServerPlayerEntity) player, NetworkHelper.SYNC_EFFECT_SPUMAPET, buf);
        }
    }

    public boolean shouldContinue() {
        if (this.spumaEntity.getTargetExplosionPos() != null)
            return true;

        return false;
    }

    void moveToTargetExplosion() {
        this.spumaEntity.getNavigation().startMovingTo(this.spumaEntity.getTargetExplosionPos().getX(),
                this.spumaEntity.getTargetExplosionPos().getY(),
                this.spumaEntity.getTargetExplosionPos().getZ(), 1);
    }
}
