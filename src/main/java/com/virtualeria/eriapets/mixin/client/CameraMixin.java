package com.virtualeria.eriapets.mixin.client;

import com.virtualeria.eriapets.access.PlayerEntityDuck;
import com.virtualeria.eriapets.entities.MimihoEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {
    @Shadow
    private float cameraY;
    @Shadow
    private float lastCameraY;

    @Shadow
    protected void moveBy(double x, double y, double z) {
    }

    @Shadow
    private double clipToSpace(double desiredCameraDistance) {
        return desiredCameraDistance;
    }

    @Shadow
    protected void setPos(double x, double y, double z) {
        this.setPos(new Vec3d(x, y, z));
    }

    @Shadow
    protected void setPos(Vec3d pos) {
    }

    @Shadow
    protected void setRotation(float yaw, float pitch) {
    }

    @Inject(at = @At("TAIL"), method = "update", cancellable = true)
    public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (focusedEntity instanceof PlayerEntity) {
            Entity ownedEntity = focusedEntity.world.getEntityById(((PlayerEntityDuck) focusedEntity).getOwnedPetID());
            if (ownedEntity != null && ownedEntity instanceof MimihoEntity && ((MimihoEntity) ownedEntity).isPlayerEated()) {
                this.setPos(MathHelper.lerp((double) tickDelta, ownedEntity.prevX, ownedEntity.getX()),
                        MathHelper.lerp((double) tickDelta, ownedEntity.prevY, ownedEntity.getY()) + (double) MathHelper.lerp(tickDelta, this.lastCameraY, this.cameraY),
                        MathHelper.lerp((double) tickDelta, ownedEntity.prevZ, ownedEntity.getZ()));
                this.moveBy(0, -0.4D, 0.0D);
            }
        }
    }

}