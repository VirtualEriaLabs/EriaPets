package com.virtualeria.eriapets.entities;

import com.virtualeria.eriapets.utils.Constants;
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
    public static final EntityType<SlimerPetEntity> SLIMER_PET_ENTITY;
    public static final EntityType<PinguPetEntity> PINGU_PET_ENTITY;
    public static final EntityType<FlinchPetEntity> FLINCH_PET_ENTITY;
    public static final EntityType<GnomePetEntity> GNOME_PET_ENTITY;
    public static final EntityType<UsagiPetEntity> USAGI_PET_ENTITY;

    static{
        BASE_PET_ENTITY = buildEntity(BasePetEntity::new,BasePetEntity.class,0.3F,0.5F, SpawnGroup.CREATURE);
        OTHO_PET_ENTITY = buildEntity(OthoPetEntity::new,OthoPetEntity.class,0.3F,0.5F, SpawnGroup.CREATURE);
        SLIMER_PET_ENTITY = buildEntity(SlimerPetEntity::new,SlimerPetEntity.class,0.3F,0.5F, SpawnGroup.CREATURE);
        PINGU_PET_ENTITY = buildEntity(PinguPetEntity::new,PinguPetEntity.class,0.3F,0.5F, SpawnGroup.CREATURE);
        FLINCH_PET_ENTITY = buildEntity(FlinchPetEntity::new,FlinchPetEntity.class,0.3F,0.5F, SpawnGroup.CREATURE);
        GNOME_PET_ENTITY = buildEntity(GnomePetEntity::new,GnomePetEntity.class,0.3F,0.7F, SpawnGroup.CREATURE);
        USAGI_PET_ENTITY = buildEntity(UsagiPetEntity::new,UsagiPetEntity.class,0.3F,0.7F, SpawnGroup.CREATURE);
    }

    public static void initialize(){
        FabricDefaultAttributeRegistry.register(BASE_PET_ENTITY, BasePetEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(OTHO_PET_ENTITY, BasePetEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(SLIMER_PET_ENTITY, BasePetEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(PINGU_PET_ENTITY, BasePetEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(FLINCH_PET_ENTITY, BasePetEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(GNOME_PET_ENTITY, GnomePetEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(USAGI_PET_ENTITY, BasePetEntity.createMobAttributes());

        Registry.register(Registry.ENTITY_TYPE, new Identifier(Constants.ModID, BasePetEntity.petName),BASE_PET_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Constants.ModID, OthoPetEntity.petName),OTHO_PET_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Constants.ModID, SlimerPetEntity.petName),SLIMER_PET_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Constants.ModID, PinguPetEntity.petName),PINGU_PET_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Constants.ModID, GnomePetEntity.petName),GNOME_PET_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Constants.ModID, FlinchPetEntity.petName),FLINCH_PET_ENTITY);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Constants.ModID, UsagiPetEntity.petName),USAGI_PET_ENTITY);

    }
    public static <T extends Entity> EntityType<T> buildEntity(EntityType.EntityFactory<T> entity, Class<T> entityClass, float width, float height, SpawnGroup group) {
        String name = entityClass.getSimpleName().toLowerCase();
        return FabricEntityTypeBuilder.create(group,entity).dimensions(EntityDimensions.fixed(width, height)).build();
    }
}
