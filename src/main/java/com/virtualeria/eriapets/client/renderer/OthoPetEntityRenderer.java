package com.virtualeria.eriapets.client.renderer;


import com.virtualeria.eriapets.client.model.OthoPetEntityModel;

import com.virtualeria.eriapets.entities.OthoPetEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;

import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class OthoPetEntityRenderer extends GeoEntityRenderer<OthoPetEntity> {

    public OthoPetEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new OthoPetEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

}
