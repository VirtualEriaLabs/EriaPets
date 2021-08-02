package com.virtualeria.eriapets.mixin;


import com.virtualeria.eriapets.events.SlimerFallDamageCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class HandleFallDamageMixin {

    @Inject(at = @At("HEAD"), method = "handleFallDamage", cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir){
        PlayerEntity player = (PlayerEntity) (Object) this;

        ActionResult result = SlimerFallDamageCallback.EVENT.invoker().interact((PlayerEntity) (Object) this,fallDistance);

        if(result == ActionResult.SUCCESS) {
            Vec3d vec3d = player.getVelocity();
            if (vec3d.y < 0.0D) {
                double d = player instanceof LivingEntity ? 1.0D : 0.8D;
                System.out.println(fallDistance);
                player.setVelocity(vec3d.x,  (fallDistance/2 *0.15) * d, vec3d.z);
                player.velocityModified = true;
            }
            cir.cancel();
        }
    }
}
