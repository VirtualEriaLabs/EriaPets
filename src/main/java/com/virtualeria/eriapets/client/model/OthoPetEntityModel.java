package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.EriaPetsMain;
import com.virtualeria.eriapets.entities.OthoPetEntity;
import com.virtualeria.eriapets.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import java.sql.Timestamp;

public class OthoPetEntityModel extends AnimatedGeoModel<OthoPetEntity> {

    @Override
    public Identifier getModelLocation(OthoPetEntity othoPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.geoDirectory, OthoPetEntity.petName));
    }

    @Override
    public Identifier getTextureLocation(OthoPetEntity othoPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.texturesDirectory, OthoPetEntity.petName));
    }

    @Override
    public Identifier getAnimationFileLocation(OthoPetEntity othoPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.animationsDirectory, OthoPetEntity.petName));
    }

    @Override
    public void setLivingAnimations(OthoPetEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 360F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 360F));
    }
}
