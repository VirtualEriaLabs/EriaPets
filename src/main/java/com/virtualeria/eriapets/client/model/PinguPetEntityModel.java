package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.EriaPetsMain;
import com.virtualeria.eriapets.entities.PinguPetEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PinguPetEntityModel extends AnimatedGeoModel<PinguPetEntity> {


    @Override
    public Identifier getModelLocation(PinguPetEntity pinguPetEntity) {
        return new Identifier(EriaPetsMain.ModID, "geo/pingu.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PinguPetEntity pinguPetEntity) {
        return new Identifier(EriaPetsMain.ModID, "textures/pingu.png");
    }

    @Override
    public Identifier getAnimationFileLocation(PinguPetEntity pinguPetEntity) {
        return new Identifier(EriaPetsMain.ModID, "animations/pingu.animation.json");
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
