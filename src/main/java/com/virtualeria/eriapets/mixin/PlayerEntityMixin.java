package com.virtualeria.eriapets.mixin;

import com.virtualeria.eriapets.access.PlayerEntityDuck;
import com.virtualeria.eriapets.entities.FlinchPetEntity;
import com.virtualeria.eriapets.entities.UsagiPetEntity;
import com.virtualeria.eriapets.utils.PetUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity implements PlayerEntityDuck {

    @Shadow
    @Final
    private final PlayerAbilities abilities = new PlayerAbilities();
    @Shadow
    @Final
    protected HungerManager hungerManager = new HungerManager();

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

    @Inject(method = "jump", at = @At("TAIL"))
    public void jump(CallbackInfo ci){
        Entity ownedEntity = world.getEntityById(((PlayerEntityDuck) this).getOwnedPetID());
        if (ownedEntity instanceof UsagiPetEntity && ((UsagiPetEntity) ownedEntity).isAlive()){
            double jumpPower = 0.6;
            Vec3d vec3d = this.getVelocity();
            this.setVelocity(vec3d.x, jumpPower, vec3d.z);

            if (this.isSprinting() ) {
                float f = this.getYaw() * 0.017453292F;
                this.setVelocity(this.getVelocity().add((double)(-MathHelper.sin(f) * jumpPower), 0.0D, (double)(MathHelper.cos(f) * jumpPower)));
                this.addExhaustion(1.1f);
            }

            this.velocityDirty = true;
        }
    }
    @Shadow
    @Final
    public void addExhaustion(float exhaustion) {
        if (!this.abilities.invulnerable) {
            if (!this.world.isClient) {
                this.hungerManager.addExhaustion(exhaustion);
            }

        }
    }

}
