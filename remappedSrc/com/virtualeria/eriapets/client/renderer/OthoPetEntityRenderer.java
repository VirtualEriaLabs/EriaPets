package com.virtualeria.eriapets.client.renderer;


import com.virtualeria.eriapets.client.model.OthoPetEntityModel;

import com.virtualeria.eriapets.entities.OthoPetEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;

import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class OthoPetEntityRenderer extends GeoEntityRenderer<OthoPetEntity> {

    public OthoPetEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new OthoPetEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

    @Override
    public void render(GeoModel model, OthoPetEntity animatable, float partialTicks, RenderLayer type, MatrixStack matrixStackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        if(animatable.abilityIsCooledDown()) {
            model.getBone("shell").get().setHidden(false);
        }else model.getBone("shell").get().setHidden(true);
    }
}
