package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.entities.CasperEntity;
import com.virtualeria.eriapets.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CasperEntityModel extends AnimatedGeoModel<CasperEntity> {

  @Override
  public Identifier getModelLocation(CasperEntity casperEntity) {
    return new Identifier(Constants.ModID,
        String.format(Constants.geoDirectory, CasperEntity.petName));
  }

  @Override
  public Identifier getTextureLocation(CasperEntity casperEntity) {
    return new Identifier(Constants.ModID,
        String.format(Constants.texturesDirectory, CasperEntity.petName));
  }

  @Override
  public Identifier getAnimationFileLocation(CasperEntity casperEntity) {
    return new Identifier(Constants.ModID,
        String.format(Constants.animationsDirectory, CasperEntity.petName));
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void setLivingAnimations(CasperEntity casperEntity, Integer uniqueID,
                                  AnimationEvent customPredicate) {
    super.setLivingAnimations(casperEntity, uniqueID, customPredicate);
    IBone head = this.getAnimationProcessor().getBone("head");

    EntityModelData extraData =
        (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
    head.setRotationX((extraData.headPitch) * ((float) Math.PI / 360F));
    head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 360F));
  }
}
