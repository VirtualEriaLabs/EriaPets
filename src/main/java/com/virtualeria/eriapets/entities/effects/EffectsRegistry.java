package com.virtualeria.eriapets.entities.effects;

import com.virtualeria.eriapets.utils.Constants;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;


public class EffectsRegistry {

    public static final StatusEffect ELECTROCUTE = new ElectrocuteStatusEffect().addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, UUID.randomUUID().toString(), -0.15000000596046448D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

    public static void  initialize(){
        Registry.register(Registry.STATUS_EFFECT, new Identifier(Constants.ModID, "electrocute"), ELECTROCUTE);
    }
}
