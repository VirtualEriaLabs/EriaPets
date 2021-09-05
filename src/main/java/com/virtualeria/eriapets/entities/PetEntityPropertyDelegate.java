package com.virtualeria.eriapets.entities;

import net.minecraft.screen.PropertyDelegate;

public class PetEntityPropertyDelegate implements PropertyDelegate {
    private final BasePetEntity petEntity;

    public PetEntityPropertyDelegate(BasePetEntity petEntity){
        this.petEntity = petEntity;
    }

    @Override
    public int get(int index) {
        switch(index){
            case 0:
                return (int)petEntity.getHealth();
            case 1:
                return (int)petEntity.getMaxHealth();
            case 2:
                return (int)petEntity.getHungry();
            case 3:
                //TODO: Get max hungry
                return 100;
            case 4:
                return (int)petEntity.getHappiness();
            case 5:
                //TODO: Get max happiness
                return 100;
            default:
                return 0;
        }
    }

    @Override
    public void set(int index, int value) {
        switch(index){
            case 0:
                petEntity.setHealth(value);
            case 1:
                //TODO Set max health
            case 2:
                petEntity.setHungry(value);
            case 3:
                //TODO: Set max hungry
            case 4:
                petEntity.setHappiness(value);
            case 5:
                //TODO: Set max happiness
        }
    }

    @Override
    public int size() {
        return 6;
    }
}
