package com.virtualeria.eriapets.client.gui;

import com.virtualeria.eriapets.entities.BasePetEntity;
import com.virtualeria.eriapets.utils.Constants;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class ScreenHandlerRegister {

    public static ScreenHandlerType<PetGuiDescription> PET_SCREEN_HANDLER_TYPE;

    public static void initialize(){
        PET_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerExtended(new Identifier(Constants.ModID,"pet_entity_gui"), (syncId, inventory, buf) -> {
            BlockPos pos = buf.readBlockPos();
            int entityId = buf.readInt();
            BasePetEntity petEntity;
            Optional<Entity> optional = Optional.ofNullable(inventory.player.world.getEntityById(entityId));
            if (optional.isPresent()) {
                petEntity = (BasePetEntity) optional.get();
            } else
                throw new AssertionError("Failed to get PetEntity in GUI for: " + entityId + " and player: " + inventory.player.getDisplayName().asString());
            return new PetGuiDescription(syncId, inventory, ScreenHandlerContext.create(inventory.player.world, pos), petEntity);
        });
    }
}
