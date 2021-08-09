package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.entities.BasePetEntity;
import com.virtualeria.eriapets.entities.FlinchPetEntity;
import com.virtualeria.eriapets.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class FlinchPetEntityModel extends AnimatedGeoModel<FlinchPetEntity> {
    @Override
    public Identifier getModelLocation(FlinchPetEntity flinchPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.geoDirectory, FlinchPetEntity.petName));
    }

    @Override
    public Identifier getTextureLocation(FlinchPetEntity flinchPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.texturesDirectory,FlinchPetEntity.petName));
    }

    @Override
    public Identifier getAnimationFileLocation(FlinchPetEntity flinchPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.animationsDirectory,FlinchPetEntity.petName));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void setLivingAnimations(FlinchPetEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 360F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 360F));
    }
}
