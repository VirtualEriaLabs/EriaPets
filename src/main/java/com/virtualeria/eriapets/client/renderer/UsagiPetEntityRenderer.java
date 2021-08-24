package com.virtualeria.eriapets.client.renderer;

import com.virtualeria.eriapets.client.model.UsagiPetEntityModel;
import com.virtualeria.eriapets.entities.UsagiPetEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;

import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class UsagiPetEntityRenderer extends GeoEntityRenderer<UsagiPetEntity> {
    protected UsagiPetEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new UsagiPetEntityModel());
    }
}
