package com.virtualeria.eriapets.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {

    private float offset  = 0;
    Vec3d lastCameraPos = new Vec3d(0,5,0);
    @Inject(at = @At("TAIL"), method = "update", cancellable = true)
    public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci){

        if(focusedEntity != null){
            System.out.println(tickDelta);
            //focusedEntity.getX()+2,focusedEntity.getY()+1,focusedEntity.getZ()
            Vec3d lerpedCamera = lastCameraPos.lerp(new Vec3d(focusedEntity.getX()+2,focusedEntity.getY()+1,focusedEntity.getZ() - 5),tickDelta * 0.01);


            System.out.println(focusedEntity.getPos());
            System.out.println(lastCameraPos);
            this.setPos(lerpedCamera);
            lastCameraPos =lerpedCamera;

           float  pitch = (float) Math.asin(-focusedEntity.getY());
            float yaw = (float) Math.atan2(focusedEntity.getX(), focusedEntity.getZ());
            Direction direction = ((LivingEntity)focusedEntity).getSleepingDirection();
            this.setRotation(180,0);

        }



    }

    public  double lerp(double a, double b, double amt) {
        amt = clamp(amt, 1F, 0F);// ww  w . j  a  va  2 s  . com
        return a * amt + b * (1D - amt);
    }
    public  double clamp(double val, double max, double min) {
        return val > max ? max : val < min ? min : val;
    }

    @Shadow
    protected void moveBy(double x, double y, double z) {}
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
    protected void setRotation(float yaw, float pitch) {}
}
