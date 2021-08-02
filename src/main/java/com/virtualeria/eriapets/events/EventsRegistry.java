package com.virtualeria.eriapets.events;

import com.virtualeria.eriapets.entities.OthoPetEntity;
import com.virtualeria.eriapets.entities.SlimerPetEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Box;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class EventsRegistry {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void registerEvents() {
        othoShellBreakCallbackEvent();
        slimerFallDamageCallback();

    }

    public static void slimerFallDamageCallback() {

        SlimerFallDamageCallback.EVENT.register((player, fallDistance) -> {
            PlayerEntity playerEntity = player;

            float radius = 10;
            float maxFallDistance = 14;

            List<SlimerPetEntity> listEntities = player.getEntityWorld().getEntitiesByClass(SlimerPetEntity.class, new Box(player.getX() - radius, 0, player.getZ() - radius, player.getX() + radius, 256, player.getZ() + radius), SlimerPetEntity::isAlive);

            for (Entity entidad : listEntities) {
                SlimerPetEntity entity = (SlimerPetEntity) entidad;
                boolean isOwnerOfEntity = entity.isOwner(playerEntity.getUuid());
                boolean isEntityAlive = entity.getCustomDeath() == 0;

                if (isOwnerOfEntity && isEntityAlive && fallDistance < maxFallDistance) {
                    entity.useAbility();

                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.FAIL;
        });
    }

    public static void othoShellBreakCallbackEvent() {
        OthoShellBreakCallback.EVENT.register((player) -> {
            ServerPlayerEntity playerEntity = (ServerPlayerEntity) player;

            float radius = 10;
            List<OthoPetEntity> listEntities = player.getEntityWorld().getEntitiesByClass(OthoPetEntity.class, new Box(player.getX() - radius, 0, player.getZ() - radius, player.getX() + radius, 256, player.getZ() + radius), OthoPetEntity::isAlive);
            for (Entity entidad : listEntities) {
                OthoPetEntity othoEntity = (OthoPetEntity) entidad;
                boolean isOwnerOfEntity = othoEntity.isOwner(playerEntity.getUuid());
                boolean isEntityAlive = othoEntity.getCustomDeath() == 0;
                if (isOwnerOfEntity && isEntityAlive && othoEntity.abilityIsCooledDown()) {
                    othoEntity.useAbility();
                    othoEntity.playAbilitySound();
                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.FAIL;
        });
    }
}
