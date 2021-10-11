package com.virtualeria.eriapets.mixin;


import com.mojang.authlib.GameProfile;
import com.virtualeria.eriapets.access.PlayerEntityDuck;
import com.virtualeria.eriapets.entities.MimihoEntity;
import com.virtualeria.eriapets.events.OthoShellBreakCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;


import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ServerPlayerEntity.class)
public class PlayerDamageMixin extends PlayerEntity {


    public PlayerDamageMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void damage(DamageSource source, float amount, final CallbackInfoReturnable<Boolean> info) {
        ActionResult result = OthoShellBreakCallback.EVENT.invoker().interact((ServerPlayerEntity) (Object) this);
        Entity ownedEntity = this.world.getEntityById(((PlayerEntityDuck) this).getOwnedPetID());

        if (result == ActionResult.SUCCESS || ownedEntity != null && ownedEntity instanceof MimihoEntity && ((MimihoEntity) ownedEntity).isPlayerEated() ) {
            info.cancel();
        }
    }


    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
