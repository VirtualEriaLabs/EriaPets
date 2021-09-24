package com.virtualeria.eriapets.client.renderer;

import com.virtualeria.eriapets.client.model.SpumaEntityModel;
import com.virtualeria.eriapets.entities.PinguPetEntity;
import com.virtualeria.eriapets.entities.SpumaEntity;
import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


@Environment(EnvType.CLIENT)
public class SpumaEntityRenderer extends GeoEntityRenderer<SpumaEntity> {

  public SpumaEntityRenderer(EntityRendererFactory.Context ctx) {
    super(ctx, new SpumaEntityModel());
    this.shadowRadius = 0.3F;
  }

  @Override
  public void render(SpumaEntity entity, float entityYaw, float partialTicks,
                     MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                     int packedLightIn) {
    super.render(entity, entityYaw, partialTicks, matrices, vertexConsumers, packedLightIn);

  }
  @Override
  public void renderEarly(SpumaEntity animatable, MatrixStack stackIn, float ticks, VertexConsumerProvider renderTypeBuffer,
                          VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue,
                          float partialTicks) {
    float scale = 0.5f;
    stackIn.scale(scale, scale, scale);
  }
}
