package com.virtualeria.eriapets.client.renderer;

import com.virtualeria.eriapets.client.model.SparkyEntityModel;
import com.virtualeria.eriapets.entities.SparkyEntity;



import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


@Environment(EnvType.CLIENT)
public class SparkyEntityRenderer extends GeoEntityRenderer<SparkyEntity> {

  public SparkyEntityRenderer(EntityRendererFactory.Context ctx) {
    super(ctx, new SparkyEntityModel());
    this.shadowRadius = 0.3F;
  }

  @Override
  public void render(SparkyEntity entity, float entityYaw, float partialTicks,
                     MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                     int packedLightIn) {
    super.render(entity, entityYaw, partialTicks, matrices, vertexConsumers, packedLightIn);
  }

  @Override
  public void renderEarly(SparkyEntity animatable, MatrixStack stackIn, float ticks, VertexConsumerProvider renderTypeBuffer,
                          VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue,
                          float partialTicks) {
    float scale = 0.5f;
    stackIn.scale(scale, scale, scale);
  }
}
