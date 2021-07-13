package com.virtualeria.eriapets;

import com.virtualeria.eriapets.entities.BasePetEntity;
import com.virtualeria.eriapets.entities.EntityRegistryPets;
import net.fabricmc.api.ModInitializer;
import software.bernie.geckolib3.GeckoLib;

public class EriaPetsMain implements ModInitializer {

    public static String ModID = "eriapets";


    @Override
    public void onInitialize() {

        System.out.println("[EriaPets] Initialize");
        GeckoLib.initialize();
        EntityRegistryPets.initialize();

    }


}
