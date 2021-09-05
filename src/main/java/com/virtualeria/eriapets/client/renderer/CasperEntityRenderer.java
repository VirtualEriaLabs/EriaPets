package com.virtualeria.eriapets.client.renderer;

import com.virtualeria.eriapets.client.model.CasperEntityModel;
import com.virtualeria.eriapets.entities.CasperEntity;

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
public class CasperEntityRenderer extends GeoEntityRenderer<CasperEntity> {

  public CasperEntityRenderer(EntityRendererFactory.Context ctx) {
    super(ctx, new CasperEntityModel());
    this.shadowRadius = 0.3F;
  }

  @Override
  public void render(GeoModel model, CasperEntity animatable, float partialTicks, RenderLayer type, MatrixStack matrixStackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
    super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    if(animatable.abilityIsCooledDown()) {
      model.getBone("mask").get().setHidden(false);
    }else model.getBone("mask").get().setHidden(true);

  }


}
