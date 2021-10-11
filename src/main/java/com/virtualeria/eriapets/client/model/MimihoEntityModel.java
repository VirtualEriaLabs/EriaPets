package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.entities.MimihoEntity;
import com.virtualeria.eriapets.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MimihoEntityModel extends AnimatedGeoModel<MimihoEntity> {

    @Override
    public Identifier getModelLocation(MimihoEntity mimihoEntity) {
        return new Identifier(Constants.ModID,
                String.format(Constants.geoDirectory, MimihoEntity.petName));
    }

    @Override
    public Identifier getTextureLocation(MimihoEntity mimihoEntity) {
        return new Identifier(Constants.ModID,
                String.format(Constants.texturesDirectory, MimihoEntity.petName));
    }

    @Override
    public Identifier getAnimationFileLocation(MimihoEntity mimihoEntity) {
        return new Identifier(Constants.ModID,
                String.format(Constants.animationsDirectory, MimihoEntity.petName));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void setLivingAnimations(MimihoEntity mimihoEntity, Integer uniqueID,
                                    AnimationEvent customPredicate) {
        super.setLivingAnimations(mimihoEntity, uniqueID, customPredicate);


        if (mimihoEntity.getOwner() != null && mimihoEntity.isPlayerEated()) {
            mimihoEntity.setPosition(mimihoEntity.getPos().lerp(mimihoEntity.getOwner().getPos(), 0.2));
            mimihoEntity.setHeadYaw(mimihoEntity.getOwner().getHeadYaw());
            mimihoEntity.setYaw(mimihoEntity.getOwner().getYaw());
        }

    }
}
