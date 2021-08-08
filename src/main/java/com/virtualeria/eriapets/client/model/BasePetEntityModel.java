package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.EriaPetsMain;
import com.virtualeria.eriapets.entities.BasePetEntity;
import com.virtualeria.eriapets.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BasePetEntityModel extends AnimatedGeoModel<BasePetEntity> {

    @Override
    public Identifier getModelLocation(BasePetEntity basePetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.geoDirectory,BasePetEntity.petName));
    }

    @Override
    public Identifier getTextureLocation(BasePetEntity basePetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.texturesDirectory,BasePetEntity.petName));
    }

    @Override
    public Identifier getAnimationFileLocation(BasePetEntity basePetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.animationsDirectory,BasePetEntity.petName));
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
