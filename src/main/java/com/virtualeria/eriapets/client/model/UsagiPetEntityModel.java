package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.entities.UsagiPetEntity;
import com.virtualeria.eriapets.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;


public class UsagiPetEntityModel extends AnimatedGeoModel<UsagiPetEntity> {
    @Override
    public Identifier getModelLocation(UsagiPetEntity object) {
        return new Identifier(Constants.ModID, String.format(Constants.geoDirectory, UsagiPetEntity.petName));
    }

    @Override
    public Identifier getTextureLocation(UsagiPetEntity object) {
        return new Identifier(Constants.ModID, String.format(Constants.texturesDirectory, UsagiPetEntity.petName));
    }

    @Override
    public Identifier getAnimationFileLocation(UsagiPetEntity animatable) {
        return new Identifier(Constants.ModID, String.format(Constants.animationsDirectory, UsagiPetEntity.petName));
    }

    @Override
    public void setLivingAnimations(UsagiPetEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 360F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 360F));

    }
}
