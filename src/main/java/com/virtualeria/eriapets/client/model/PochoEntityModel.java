package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.entities.PochoEntity;
import com.virtualeria.eriapets.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PochoEntityModel extends AnimatedGeoModel<PochoEntity> {

  @Override
  public Identifier getModelLocation(PochoEntity pochoEntity) {
    return new Identifier(Constants.ModID,
        String.format(Constants.geoDirectory, PochoEntity.petName));
  }

  @Override
  public Identifier getTextureLocation(PochoEntity pochoEntity) {
    return new Identifier(Constants.ModID,
        String.format(Constants.texturesDirectory, PochoEntity.petName));
  }

  @Override
  public Identifier getAnimationFileLocation(PochoEntity pochoEntity) {
    return new Identifier(Constants.ModID,
        String.format(Constants.animationsDirectory, PochoEntity.petName));
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void setLivingAnimations(PochoEntity pochoEntity, Integer uniqueID,
                                  AnimationEvent customPredicate) {
    super.setLivingAnimations(pochoEntity, uniqueID, customPredicate);
    IBone head = this.getAnimationProcessor().getBone("head");

    EntityModelData extraData =
        (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
    head.setRotationX((extraData.headPitch) * ((float) Math.PI / 360F));
    head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 360F));
  }
}
