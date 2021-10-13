package com.virtualeria.eriapets.entities.AnimationController;

import com.virtualeria.eriapets.entities.MimihoEntity;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class MimihoAnimationController {
    MimihoEntity mimiho;
    boolean abilityStaticPose = false;

    public MimihoAnimationController(MimihoEntity mimihoEntity) {
        this.mimiho = mimihoEntity;
    }

    private <E extends IAnimatable> PlayState abilityAnimationController(AnimationEvent<E> event) {
        if (mimiho.isPlayerInside()) {
            if (!abilityStaticPose) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ability", false));
                abilityStaticPose = true;
            } else if (event.getController().getAnimationState() == AnimationState.Stopped) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.abilitystaticpose", false));
            }
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.idle", false));
        abilityStaticPose = false;
        return null;
    }

    private <E extends IAnimatable> PlayState walkAnimationController(AnimationEvent<E> event) {
        if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F) && mimiho.getCustomDeath() == 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.walk", false));
            return PlayState.CONTINUE;
        }
        return null;
    }

    private <E extends IAnimatable> PlayState deathAnimationController(AnimationEvent<E> event) {

        boolean shouldLoop;
        String animationClip;
        if (this.mimiho.getCustomDeath() > 0) {
            if (this.mimiho.getCustomDeath() == 2 && event.getController().getAnimationState() == AnimationState.Stopped) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.deathPos", true));
            } else if (this.mimiho.getCustomDeath() == 1) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.death", false));
                this.mimiho.setCustomDeath(2);
            }

            return PlayState.CONTINUE;
        }
        return null;
    }

    public <E extends IAnimatable> PlayState predicateMimiho(AnimationEvent<E> event) {
        PlayState playState = null;
        boolean shouldLoop = true;
        String idleAnimation = "animation.idle";

        playState = abilityAnimationController(event);
        if(playState != null) return playState;

        playState = walkAnimationController(event);
        if(playState != null) return playState;

        playState = deathAnimationController(event);
        if(playState != null) return playState;

        if (event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(idleAnimation, shouldLoop));
        }

        return PlayState.CONTINUE;
    }
}
