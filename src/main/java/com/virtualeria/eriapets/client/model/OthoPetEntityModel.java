package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.EriaPetsMain;
import com.virtualeria.eriapets.entities.BasePetEntity;
import com.virtualeria.eriapets.entities.OthoPetEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class OthoPetEntityModel extends AnimatedGeoModel<OthoPetEntity> {

    @Override
    public Identifier getModelLocation(OthoPetEntity othoPetEntity) {
        return new Identifier(EriaPetsMain.ModID, "geo/otho.geo.json");
    }

    @Override
    public Identifier getTextureLocation(OthoPetEntity othoPetEntity) {
        return new Identifier(EriaPetsMain.ModID, "textures/otho.png");
    }

    @Override
    public Identifier getAnimationFileLocation(OthoPetEntity othoPetEntity) {
        return new Identifier(EriaPetsMain.ModID, "animations/otho.animation.json");
    }

    @Override
    public void setLivingAnimations(OthoPetEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        IBone shell = this.getAnimationProcessor().getBone("shell");


        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 360F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 360F));
    }
}
