package com.virtualeria.eriapets.mixin.client;

import com.virtualeria.eriapets.access.PlayerEntityDuck;
import com.virtualeria.eriapets.entities.MimihoEntity;
import com.virtualeria.eriapets.utils.Constants;
import net.minecraft.block.IceBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.gui.hud.InGameHud.class)
public class InGameHudMixin {

    @Shadow
    private void renderOverlay(Identifier texture, float opacity) {
    }


    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        Entity ownedEntity = MinecraftClient.getInstance().player.world.getEntityById(((PlayerEntityDuck) MinecraftClient.getInstance().player).getOwnedPetID());
        if (ownedEntity instanceof MimihoEntity && ((MimihoEntity) ownedEntity).isPlayerEated())
            this.renderOverlay(new Identifier(Constants.ModID, "textures/mimihooverlay.png"), 0.5F);

    }
}
