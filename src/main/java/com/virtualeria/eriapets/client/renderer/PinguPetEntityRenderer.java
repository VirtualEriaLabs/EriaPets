package com.virtualeria.eriapets.client.renderer;

import com.virtualeria.eriapets.client.model.PinguPetEntityModel;
import com.virtualeria.eriapets.entities.PinguPetEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PinguPetEntityRenderer extends GeoEntityRenderer<PinguPetEntity> {
    public PinguPetEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PinguPetEntityModel());
    }

    @Override
    public void renderEarly(PinguPetEntity animatable, MatrixStack stackIn, float ticks, VertexConsumerProvider renderTypeBuffer,
                            VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue,
                            float partialTicks) {
        float scale = 0.6f;
        stackIn.scale(scale, scale, scale);
    }


}
