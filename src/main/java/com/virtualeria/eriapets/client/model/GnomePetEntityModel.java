package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.entities.GnomePetEntity;
import com.virtualeria.eriapets.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class GnomePetEntityModel extends AnimatedGeoModel<GnomePetEntity> {
    @Override
    public Identifier getModelLocation(GnomePetEntity flinchPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.geoDirectory, GnomePetEntity.petName));
    }

    @Override
    public Identifier getTextureLocation(GnomePetEntity flinchPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.texturesDirectory, GnomePetEntity.petName));
    }

    @Override
    public Identifier getAnimationFileLocation(GnomePetEntity flinchPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.animationsDirectory, GnomePetEntity.petName));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void setLivingAnimations(GnomePetEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 360F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 360F));
    }

}
