package com.virtualeria.eriapets.events;

import com.virtualeria.eriapets.entities.OthoPetEntity;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface OthoShellBreakCallback {
    Event<OthoShellBreakCallback> EVENT = EventFactory.createArrayBacked(OthoShellBreakCallback.class,
            (listeners) -> (player) -> {
                for (OthoShellBreakCallback listener : listeners) {
                    ActionResult result = listener.interact(player);

                    if(result != ActionResult.PASS) {
                        return result;
                    }

                }

                return ActionResult.FAIL;
            });

    ActionResult interact(PlayerEntity player);
}
