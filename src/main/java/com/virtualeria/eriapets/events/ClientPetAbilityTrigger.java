package com.virtualeria.eriapets.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface ClientPetAbilityTrigger {

    Event<ClientPetAbilityTrigger> EVENT = EventFactory.createArrayBacked(ClientPetAbilityTrigger.class,
            (listeners) -> (player) -> {
                for (ClientPetAbilityTrigger listener : listeners) {
                    ActionResult result = listener.interact(player);
                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.FAIL;
            });

    ActionResult interact(PlayerEntity player);
}
