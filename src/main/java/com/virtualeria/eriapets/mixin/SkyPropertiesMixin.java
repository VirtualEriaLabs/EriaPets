package com.virtualeria.eriapets.mixin;

import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkyProperties.Overworld.class)
public class SkyPropertiesMixin extends SkyProperties {
    public SkyPropertiesMixin(float cloudsHeight, boolean alternateSkyColor, SkyType skyType, boolean brightenLighting, boolean darkened) {
        super(cloudsHeight, alternateSkyColor, skyType, brightenLighting, darkened);
    }

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return null;
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        return false;
    }

    @Inject(method = "adjustFogColor", at = @At("TAIL"), cancellable = true)
    public void adjustFogColorNew(Vec3d color, float sunHeight, CallbackInfoReturnable<Vec3d> cir) {

            cir.setReturnValue(new Vec3d(1,0,0));
    }

    @Inject(method = "useThickFog", at = @At("TAIL"), cancellable = true)
    public void useThickFog(int camX, int camY, CallbackInfoReturnable<Boolean> cir) {
      cir.setReturnValue(true);
    }

    @Override
    public SkyProperties.SkyType getSkyType() {
        return SkyType.NORMAL;
    }

}
