package com.virtualeria.eriapets.entities;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegistryPets {

    public static final EntityType<BasePetEntity> BASE_PET_ENTITY;
    public static final EntityType<OthoPetEntity> OTHO_PET_ENTITY;

    static{
        BASE_PET_ENTITY = buildEntity(BasePetEntity::new,BasePetEntity.class,0.7F,1.3F, SpawnGroup.CREATURE);
        OTHO_PET_ENTITY = buildEntity(OthoPetEntity::new,OthoPetEntity.class,0.7F,1.3F, SpawnGroup.CREATURE);
    }

    public static void initialize(){
        FabricDefaultAttributeRegistry.register(BASE_PET_ENTITY, BasePetEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(OTHO_PET_ENTITY, BasePetEntity.createMobAttributes());

        Registry.register(Registry.ENTITY_TYPE, new Identifier("eriapets", "basepet"),BASE_PET_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier("eriapets", "otho"),OTHO_PET_ENTITY);
    }
    public static <T extends Entity> EntityType<T> buildEntity(EntityType.EntityFactory<T> entity, Class<T> entityClass, float width, float height, SpawnGroup group) {
        String name = entityClass.getSimpleName().toLowerCase();
        return FabricEntityTypeBuilder.create(group,entity).dimensions(EntityDimensions.fixed(0.3f, 0.3f)).build();
    }
}
