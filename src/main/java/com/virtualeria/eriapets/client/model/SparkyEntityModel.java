package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.entities.SparkyEntity;
import com.virtualeria.eriapets.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SparkyEntityModel extends AnimatedGeoModel<SparkyEntity> {

  @Override
  public Identifier getModelLocation(SparkyEntity sparkyEntity) {
    return new Identifier(Constants.ModID,
        String.format(Constants.geoDirectory, SparkyEntity.petName));
  }

  @Override
  public Identifier getTextureLocation(SparkyEntity sparkyEntity) {
    return new Identifier(Constants.ModID,
        String.format(Constants.texturesDirectory, SparkyEntity.petName));
  }

  @Override
  public Identifier getAnimationFileLocation(SparkyEntity sparkyEntity) {
    return new Identifier(Constants.ModID,
        String.format(Constants.animationsDirectory, SparkyEntity.petName));
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void setLivingAnimations(SparkyEntity sparkyEntity, Integer uniqueID,
                                  AnimationEvent customPredicate) {
    super.setLivingAnimations(sparkyEntity, uniqueID, customPredicate);

  }
}
