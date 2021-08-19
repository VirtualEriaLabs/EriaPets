package com.virtualeria.eriapets.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.render.BackgroundRenderer.class)
public class BackgroundRenderer {
    @Inject(method = "applyFog", at = @At("TAIL"), cancellable = true)
    private static void applyFog(Camera camera, net.minecraft.client.render.BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
        RenderSystem.setShaderFogStart(0);
        RenderSystem.setShaderFogEnd(50);

    }

}
