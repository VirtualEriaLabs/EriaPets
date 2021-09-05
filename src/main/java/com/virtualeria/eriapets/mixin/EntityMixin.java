package com.virtualeria.eriapets.mixin;

import com.virtualeria.eriapets.access.EntityDuck;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Entity.class)
public abstract class EntityMixin implements EntityDuck {

    @Shadow
    @Final
    public World world;

    @Shadow
    @Final
    static
    TrackedData<Byte> FLAGS;
    @Shadow
    @Final
    protected static final int GLOWING_FLAG_INDEX = 6;
    @Mutable
    @Shadow
    @Final
    private boolean glowing;

    @Shadow public abstract boolean isGlowingLocal();

    @Inject(at = @At("HEAD"), method = "isGlowing", cancellable = true)
    public void isGlowing(CallbackInfoReturnable<Boolean> cir){

        boolean isGlowing = this.world.isClient() ? this.glowing || this.getFlag(GLOWING_FLAG_INDEX)  : this.glowing;
        cir.setReturnValue(isGlowing);
    }
    @Shadow
    protected boolean getFlag(int index) {
        return false;
    }

    public void setLocalGlowing(boolean glowing) {
        this.glowing = glowing;
    }



}
