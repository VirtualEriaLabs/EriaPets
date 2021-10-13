package com.virtualeria.eriapets.entities.ai.goals;

import com.virtualeria.eriapets.entities.MimihoEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;


public class MimihoEatPlayer extends Goal {

    MimihoEntity mimiho;
    PlayerEntity owner;
    boolean playerReached = false;
    int maxDuration = 300;
    int durationTicks = 0;

    public MimihoEatPlayer(MimihoEntity mimiho, int duration) {
        this.mimiho = mimiho;
        this.maxDuration = duration;
    }

    @Override
    public boolean canStart() {
        if (!this.mimiho.isAlive() || !this.mimiho.canUseAbility())
            return false;
        if (this.mimiho.isAbilityRunning())
            return true;

        return false;
    }

    public void start() {
        this.owner = (PlayerEntity) this.mimiho.getOwner();
    }

    public void tick() {
        if (!playerReached) {
            moveToPlayerPos();
            goToPlayer();
        } else {
            this.mimiho.setPosition(mimiho.getPos().lerp(mimiho.getOwner().getPos(), 0.5));
        }
        durationTicks++;
    }

    public void goToPlayer() {
        if (this.owner.squaredDistanceTo(this.mimiho.getX(),
                this.owner.getY(),
                this.owner.getZ()) < 1) {
            playerReached = true;
            this.mimiho.setPlayerIsInside(true);
            this.mimiho.setInvulnerable(true);
            this.mimiho.getOwner().addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, maxDuration - durationTicks, 2));
        }
    }

    void moveToPlayerPos() {
        this.mimiho.getNavigation().startMovingTo(this.owner.getX(),
                this.owner.getY(),
                this.owner.getZ(), 1);
    }

    public void stop() {
        durationTicks = 0;
        this.mimiho.setAbilityRunning(false);
        this.mimiho.useAbility();
        this.mimiho.setPlayerIsInside(false);
        this.mimiho.setInvulnerable(false);
        this.playerReached = false;
    }

    public boolean shouldContinue() {
        if (durationTicks >= maxDuration)
            return false;


        return this.canStart();
    }
}
