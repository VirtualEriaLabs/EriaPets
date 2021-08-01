package com.virtualeria.eriapets.client.model;

import com.virtualeria.eriapets.EriaPetsMain;
import com.virtualeria.eriapets.entities.OthoPetEntity;
import com.virtualeria.eriapets.entities.SlimerPetEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SlimerPetEntityModel extends AnimatedGeoModel<SlimerPetEntity> {
    @Override
    public Identifier getModelLocation(SlimerPetEntity slimerPetEntity) {
        return new Identifier(EriaPetsMain.ModID, "geo/slimer.geo.json");
    }

    @Override
    public Identifier getTextureLocation(SlimerPetEntity slimerPetEntity) {
        return new Identifier(EriaPetsMain.ModID, "textures/slimer.png");
    }

    @Override
    public Identifier getAnimationFileLocation(SlimerPetEntity slimerPetEntity) {
        return new Identifier(EriaPetsMain.ModID, "animations/slimer.animation.json");
    }
}
