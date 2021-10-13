package com.virtualeria.eriapets.entities.effects;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;


public class ElectrocuteStatusEffect extends StatusEffect {

    int eletrocuteDuration = 40;
    int electrocuteTick = 0;

    protected ElectrocuteStatusEffect() {
        super(
                StatusEffectType.HARMFUL, // whether beneficial or harmful for entities
                0xf6ff00); // color in RGB
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        electrocuteTick++;
        if (electrocuteTick > eletrocuteDuration) {
            electrocuteTick = 0;
            return true;

        }
        return false;
    }

    // This method is called when it applies the status effect.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(DamageSource.MAGIC, 0.5f);

    }
}
