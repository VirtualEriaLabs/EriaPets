package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.EriaPetsMain;
import com.virtualeria.eriapets.entities.SlimerPetEntity;
import com.virtualeria.eriapets.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SlimerPetEntityModel extends AnimatedGeoModel<SlimerPetEntity> {
    @Override
    public Identifier getModelLocation(SlimerPetEntity slimerPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.geoDirectory, SlimerPetEntity.petName));
    }

    @Override
    public Identifier getTextureLocation(SlimerPetEntity slimerPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.texturesDirectory, SlimerPetEntity.petName));
    }

    @Override
    public Identifier getAnimationFileLocation(SlimerPetEntity slimerPetEntity) {
        return new Identifier(Constants.ModID, String.format(Constants.animationsDirectory, SlimerPetEntity.petName));
    }
}
