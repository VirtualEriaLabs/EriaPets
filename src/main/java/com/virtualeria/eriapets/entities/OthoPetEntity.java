package com.virtualeria.eriapets.entities;

import com.virtualeria.eriapets.events.OthoShellBreakCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class OthoPetEntity extends  BasePetEntity{
    public OthoPetEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        registerEvents();
    }

    private void registerEvents(){
        OthoShellBreakCallback.EVENT.register((player) -> {
            ServerPlayerEntity playerEntity = (ServerPlayerEntity) player;
            if(this.getOwnerUuid() != null
                    && this.getOwnerUuid().equals(playerEntity.getUuid())
                    && this.isAlive()
                    && this.getCustomDeath() == 0)
                return ActionResult.SUCCESS;

           return  ActionResult.FAIL;
        });

    }

}
