package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.EriaPetsMain;
import com.virtualeria.eriapets.entities.BasePetEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BasePetEntityModel extends AnimatedGeoModel<BasePetEntity> {

    @Override
    public Identifier getModelLocation(BasePetEntity basePetEntity) {
        return new Identifier(EriaPetsMain.ModID, "geo/baseentity.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BasePetEntity basePetEntity) {
        return new Identifier(EriaPetsMain.ModID, "textures/baseentity.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BasePetEntity basePetEntity) {
        return new Identifier(EriaPetsMain.ModID, "animations/baseentity.animation.json");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void setLivingAnimations(BasePetEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 360F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 360F));
    }
}
