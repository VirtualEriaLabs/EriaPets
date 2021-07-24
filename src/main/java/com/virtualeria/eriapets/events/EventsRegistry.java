package com.virtualeria.eriapets.events;

import com.virtualeria.eriapets.entities.OthoPetEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Box;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class EventsRegistry {

    private static final Logger LOGGER = LogManager.getLogger();
    public static void registerEvents(){

        OthoShellBreakCallback.EVENT.register((player) -> {
            ServerPlayerEntity playerEntity = (ServerPlayerEntity) player;

            float radio = 10;
            List<OthoPetEntity> listEntities = player.getEntityWorld().getEntitiesByClass(OthoPetEntity.class,new Box(player.getX() - radio, 0, player.getZ() - radio, player.getX()  + radio, 256, player.getZ() + radio),OthoPetEntity::isAlive);

            for (Entity entidad: listEntities) {
                    OthoPetEntity othoEntity = (OthoPetEntity) entidad;
                    if (othoEntity.getOwnerUuid() != null
                            && othoEntity.getOwnerUuid().equals(playerEntity.getUuid())
                            && othoEntity.getCustomDeath() == 0
                            && othoEntity.abilityIsCooledDown()) {
                        othoEntity.useAbility();
                        othoEntity.playAbilitySound();
                        return ActionResult.SUCCESS;
                    }
            }
            return  ActionResult.FAIL;
        });
    }
}
