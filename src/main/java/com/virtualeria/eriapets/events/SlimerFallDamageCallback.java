package com.virtualeria.eriapets.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface SlimerFallDamageCallback {
    Event<SlimerFallDamageCallback> EVENT = EventFactory.createArrayBacked(SlimerFallDamageCallback.class,
            (listeners) -> (player, fallDistance) -> {
                for (SlimerFallDamageCallback listener : listeners) {
                    ActionResult result = listener.interact(player,fallDistance);
                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.FAIL;
            });

    ActionResult interact(PlayerEntity player, float fallDistance);
}
