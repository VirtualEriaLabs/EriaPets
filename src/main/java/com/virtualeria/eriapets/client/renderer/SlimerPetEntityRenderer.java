package com.virtualeria.eriapets.client.renderer;

import com.virtualeria.eriapets.client.model.SlimerPetEntityModel;
import com.virtualeria.eriapets.entities.SlimerPetEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SlimerPetEntityRenderer extends GeoEntityRenderer<SlimerPetEntity> {
    public SlimerPetEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SlimerPetEntityModel());
    }
}
