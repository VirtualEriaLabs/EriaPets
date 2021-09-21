package com.virtualeria.eriapets.mixin;


import com.virtualeria.eriapets.access.PlayerEntityDuck;
import com.virtualeria.eriapets.entities.PinguPetEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.IceBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(IceBlock.class)
public class IceBlockMixin {

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        PinguAbilityCheck(entity, world);
    }

    private void PinguAbilityCheck(Entity entity, World world) {
        double maxVelocity = 0.8;
        double xVel = Math.abs(entity.getVelocity().x);
        double zVel = Math.abs(entity.getVelocity().z);

        boolean isPlayer,isPinguPetEntity,hasVelocity = false;
        isPlayer = entity instanceof PlayerEntity;

        if (isPlayer) {
            isPinguPetEntity = world.getEntityById(((PlayerEntityDuck) entity).getOwnedPetID()) instanceof PinguPetEntity;
            hasVelocity = (xVel < maxVelocity && zVel < maxVelocity);

            if (isPinguPetEntity && hasVelocity && entity.isSprinting()){
                entity.addVelocity(entity.getVelocity().x * 0.2f, entity.getVelocity().y * 0.2f, entity.getVelocity().z * 0.2f);
                entity.velocityDirty = true;
                entity.velocityModified = true;
            }

        }

    }

}
