package com.virtualeria.eriapets.mixin;

import com.virtualeria.eriapets.access.PlayerEntityDuck;
import com.virtualeria.eriapets.entities.FlinchPetEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity implements PlayerEntityDuck {

    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    private static final TrackedData<Integer> OWNEDPETID;


    @Inject(method = "initDataTracker", at = @At("TAIL"))
    public void dataTrackerMixin(CallbackInfo callbackInfo) {
        dataTracker.startTracking(OWNEDPETID, 0);
    }

    static {
        OWNEDPETID = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    @Override
    public void setOwnedPetID(int v) {
        dataTracker.set(OWNEDPETID, v);
    }

    @Override
    public int getOwnedPetID() {
        return (Integer) dataTracker.get(OWNEDPETID);
    }


    @Inject(method = "attack", at = @At("HEAD"))
    public void attack(Entity target, CallbackInfo ci) {
        triggerFlinchEvent(target);
    }


    public void triggerFlinchEvent(Entity target) {
        Entity ownedEntity = world.getEntityById(((PlayerEntityDuck) this).getOwnedPetID());
        if (ownedEntity instanceof FlinchPetEntity)
            ((FlinchPetEntity) ownedEntity).canPoisonTarget(target);
    }


}
