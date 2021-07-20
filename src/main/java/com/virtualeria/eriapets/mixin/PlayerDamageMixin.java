package com.virtualeria.eriapets.mixin;

import com.virtualeria.eriapets.events.OthoShellBreakCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.core.jmx.Server;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ServerPlayerEntity.class)
public class PlayerDamageMixin {
    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void damage(DamageSource source, float amount, final CallbackInfoReturnable<Boolean> info){
        ActionResult result = OthoShellBreakCallback.EVENT.invoker().interact( (ServerPlayerEntity) (Object) this);

        System.out.println(result);
        if(result == ActionResult.SUCCESS) {
            info.cancel();
        }

    }
}
