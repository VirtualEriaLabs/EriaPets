package com.virtualeria.eriapets.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.virtualeria.eriapets.client.model.MimihoEntityModel;
import com.virtualeria.eriapets.entities.MimihoEntity;
import java.awt.Color;

import com.virtualeria.eriapets.utils.Constants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


@Environment(EnvType.CLIENT)
public class MimihoEntityRenderer extends GeoEntityRenderer<MimihoEntity> {

  public MimihoEntityRenderer(EntityRendererFactory.Context ctx) {
    super(ctx, new MimihoEntityModel());
    this.shadowRadius = 0.3F;
  }

  @Override
  public void render(MimihoEntity entity, float entityYaw, float partialTicks,
                     MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                     int packedLightIn) {
    super.render(entity, entityYaw, partialTicks, matrices, vertexConsumers, packedLightIn);

  }
}
