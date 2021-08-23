package com.virtualeria.eriapets.client.renderer;

import com.virtualeria.eriapets.client.model.GnomePetEntityModel;
import com.virtualeria.eriapets.entities.GnomePetEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class GnomePetEntityRenderer extends GeoEntityRenderer<GnomePetEntity> {
    protected GnomePetEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GnomePetEntityModel());
    }
}
