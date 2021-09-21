package com.virtualeria.eriapets.client;

import com.virtualeria.eriapets.events.ClientPetAbilityTrigger;
import com.virtualeria.eriapets.events.OthoShellBreakCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static void registerKeyBindings(){
        KeyBinding petAbility = KeyBindingHelper.registerKeyBinding(new KeyBinding("keybindings.eriapets.petAbility", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "keybinding.category.eriapets"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (petAbility.wasPressed()) {
                ActionResult result = ClientPetAbilityTrigger.EVENT.invoker().interact(MinecraftClient.getInstance().player);
                if(result == ActionResult.SUCCESS){
                    client.player.sendMessage(new LiteralText("Habilidad de la mascota usada"), false);
                }else{
                    client.player.sendMessage(new LiteralText("Habilidad en cooldown"), false);
                }

            }
        });
    }
}
