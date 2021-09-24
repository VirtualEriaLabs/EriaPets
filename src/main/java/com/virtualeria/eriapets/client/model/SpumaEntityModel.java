package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.entities.SpumaEntity;
import com.virtualeria.eriapets.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SpumaEntityModel extends AnimatedGeoModel<SpumaEntity> {

  @Override
  public Identifier getModelLocation(SpumaEntity spumaEntity) {
    return new Identifier(Constants.ModID,
        String.format(Constants.geoDirectory, SpumaEntity.petName));
  }

  @Override
  public Identifier getTextureLocation(SpumaEntity spumaEntity) {
    return new Identifier(Constants.ModID,
        String.format(Constants.texturesDirectory, SpumaEntity.petName));
  }

  @Override
  public Identifier getAnimationFileLocation(SpumaEntity spumaEntity) {
    return new Identifier(Constants.ModID,
        String.format(Constants.animationsDirectory, SpumaEntity.petName));
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void setLivingAnimations(SpumaEntity spumaEntity, Integer uniqueID,
                                  AnimationEvent customPredicate) {
    super.setLivingAnimations(spumaEntity, uniqueID, customPredicate);
   /* IBone head = this.getAnimationProcessor().getBone("Head");

    EntityModelData extraData =
        (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
    head.setRotationX((extraData.headPitch) * ((float) Math.PI / 360F));
    head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 360F));*/
  }
}
