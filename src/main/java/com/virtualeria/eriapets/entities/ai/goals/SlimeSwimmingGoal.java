package com.virtualeria.eriapets.entities.ai.goals;

import com.virtualeria.eriapets.entities.SpumaEntity;
import com.virtualeria.eriapets.entities.ai.MoveControls.SlimeMoveControl;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SlimeSwimmingGoal extends  Goal{
    private final SpumaEntity slime;

    public SlimeSwimmingGoal(SpumaEntity slime) {
        this.slime = slime;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
        slime.getNavigation().setCanSwim(true);
    }

    public boolean canStart() {
        return (this.slime.isTouchingWater() || this.slime.isInLava()) && this.slime.getMoveControl() instanceof SlimeMoveControl;
    }

    public void tick() {
        if (this.slime.getRandom().nextFloat() < 0.8F) {
            this.slime.getJumpControl().setActive();
        }

        ((SlimeMoveControl) this.slime.getMoveControl()).move(1.2D);
    }
}
