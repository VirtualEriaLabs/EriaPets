package com.virtualeria.eriapets.client.renderer;

import com.virtualeria.eriapets.client.model.CasperEntityModel;
import com.virtualeria.eriapets.entities.CasperEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.render.entity.EntityRendererFactory;

import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;





@Environment(EnvType.CLIENT)
public class CasperEntityRenderer extends GeoEntityRenderer<CasperEntity> {

  public CasperEntityRenderer(EntityRendererFactory.Context ctx) {
    super(ctx, new CasperEntityModel());
    this.shadowRadius = 0.3F;
  }




}
