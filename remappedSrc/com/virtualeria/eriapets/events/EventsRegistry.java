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
            List<Entity> listEntities = player.getEntityWorld().getOtherEntities((Entity)null,new Box(player.getX() - radio, player.getY() - radio, player.getZ() - radio, player.getX()  + radio, player.getY()  + radio, player.getZ() + radio));

            for (Entity entidad: listEntities) {
                if (entidad instanceof OthoPetEntity) {
                    OthoPetEntity othoEntity = (OthoPetEntity) entidad;
                    LOGGER.info("this.abilityIsCooledDown() " + othoEntity.abilityIsCooledDown());
                    LOGGER.info("this.getCustomDeath() " + othoEntity.abilityIsCooledDown());
                    LOGGER.info("this.getOwnerUuid() " + othoEntity.isAlive());
                    LOGGER.info("othoEntity.getCustomDeath() " +  othoEntity.getCustomDeath());
                    LOGGER.info("this.getOwnerUuid() " + othoEntity.getOwnerUuid());
                    if (othoEntity.getOwnerUuid() != null
                            && othoEntity.getOwnerUuid().equals(playerEntity.getUuid())
                            && othoEntity.isAlive()
                            && othoEntity.getCustomDeath() == 0
                            && othoEntity.abilityIsCooledDown()) {
                        othoEntity.useAbility();
                        othoEntity.playAbilitySound();
                        return ActionResult.SUCCESS;
                    }

                }
            }
            return  ActionResult.FAIL;
        });
    }
}
