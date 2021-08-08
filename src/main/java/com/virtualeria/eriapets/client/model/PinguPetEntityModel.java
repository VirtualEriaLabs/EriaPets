package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.EriaPetsMain;
import com.virtualeria.eriapets.entities.PinguPetEntity;
import com.virtualeria.eriapets.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PinguPetEntityModel extends AnimatedGeoModel<PinguPetEntity> {


    @Override
    public Identifier getModelLocation(PinguPetEntity pinguPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.geoDirectory, PinguPetEntity.petName));
    }

    @Override
    public Identifier getTextureLocation(PinguPetEntity pinguPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.texturesDirectory, PinguPetEntity.petName));
    }

    @Override
    public Identifier getAnimationFileLocation(PinguPetEntity pinguPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.animationsDirectory, PinguPetEntity.petName));
    }

    @Override
    public void setLivingAnimations(PinguPetEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 360F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 360F));

    }


}
