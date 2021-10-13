package com.virtualeria.eriapets.entities.ai.goals;

import com.virtualeria.eriapets.entities.SparkyEntity;
import com.virtualeria.eriapets.entities.effects.EffectsRegistry;
import com.virtualeria.eriapets.utils.NetworkHelper;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class SparkyElectrocuteEntities extends Goal {

    SparkyEntity sparkyEntity;
    boolean reachedDestionation = false;
    int duration = 0;
    int maxGoalDuration = 200;
    World world;
    int effectsTick = 0;
    final int effectsTickMax = 40;
    int electrocudeDuration = 0;
    final int electrocudeDurationMax = 100;
    List<Entity> electrocutedEntities;
    float radio = 5;

    public SparkyElectrocuteEntities(SparkyEntity sparkyEntity, World world, float radio) {
        this.sparkyEntity = sparkyEntity;
        this.world = world;
        this.radio = radio;
    }

    @Override
    public boolean canStart() {
        if (!this.sparkyEntity.isAlive())
            return false;
        if (!this.sparkyEntity.canUseAbility())
            return false;
        if (this.sparkyEntity.isAbilityRunning())
            return true;

        return false;
    }

    public void stop() {
        this.electrocudeDuration = 0;
        this.duration = 0;
        this.reachedDestionation = false;
        this.sparkyEntity.setShowElectrocuteParticles(false);
    }


    public void tick() {
        if (!reachedDestionation && duration < maxGoalDuration) {
            moveToTarget();
        } else
            electrocuteEntities();

        duration++;
    }

    void electrocuteEntities() {
        if (electrocudeDuration < electrocudeDurationMax) {
            if (effectsTick > effectsTickMax) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeInt(this.sparkyEntity.getId());
                buf.writeBlockPos(this.sparkyEntity.getTargetElectrocute());
                NetworkHelper.sendPacketToAllNerbyClients((ServerWorld) world, this.sparkyEntity.getTargetElectrocute(), NetworkHelper.SYNC_PET_SPARKY,buf);
                effectsTick = 0;
            }
            effectsTick++;
        } else {
            this.sparkyEntity.setAbilityRunning(false);
            this.sparkyEntity.setTargetElectrocute(null);
        }
        electrocudeDuration++;
    }

    void electrocute() {
        electrocutedEntities = world.getOtherEntities(this.sparkyEntity, new Box(this.sparkyEntity.getX() - radio, this.sparkyEntity.getY() - radio, this.sparkyEntity.getZ() - radio,
                this.sparkyEntity.getX() + radio, this.sparkyEntity.getY() + radio, this.sparkyEntity.getZ() + radio));

        for (Entity entity : electrocutedEntities) {
            if (entity instanceof LivingEntity && !(entity instanceof PlayerEntity))
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(EffectsRegistry.ELECTROCUTE, electrocudeDurationMax, 10, true, true));
        }

    }

    void moveToTarget() {
        if (this.sparkyEntity.squaredDistanceTo(this.sparkyEntity.getTargetElectrocute().getX(),
                this.sparkyEntity.getTargetElectrocute().getY(),
                this.sparkyEntity.getTargetElectrocute().getZ()) < 3.5) {
            reachedDestionation = true;
            electrocute();
            this.sparkyEntity.drawPetEffects(world,this.sparkyEntity.getTargetElectrocute());
        } else {
            this.sparkyEntity.getNavigation().startMovingTo(this.sparkyEntity.getTargetElectrocute().getX(),
                    this.sparkyEntity.getTargetElectrocute().getY(),
                    this.sparkyEntity.getTargetElectrocute().getZ(), 1);
        }
    }


}
