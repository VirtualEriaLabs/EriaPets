package com.virtualeria.eriapets.mixin;

import com.virtualeria.eriapets.entities.UsagiPetEntity;
import com.virtualeria.eriapets.utils.PetUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "onLandedUpon", at = @At("TAIL"))
    public void onEntityLand(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
        if (entity instanceof PlayerEntity) checkUsagiAbilityLanding((PlayerEntity) entity,pos);
    }

    public void checkUsagiAbilityLanding(PlayerEntity player,BlockPos pos){
        Entity ownedEntity = PetUtils.getPlayerOwnedPet(player);

        if(ownedEntity instanceof UsagiPetEntity && player.isSneaking() && ((UsagiPetEntity) ownedEntity).canUseAbility()){
            UsagiPetEntity usagi = (UsagiPetEntity) ownedEntity;
            usagi.smashGroundPlayer(pos);
            usagi.useAbility();
        }
    }

}
