package com.virtualeria.eriapets.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class RaycastUtils {

    public static BlockHitResult raycastFromPlayerView(PlayerEntity player, float length, World world){
        Vec3d playerDirection = player.getRotationVector().normalize();
        Vec3d fin = player.getPos().add(new Vec3d(0, 1.5, 0)).add(playerDirection.multiply(length));
        return world.raycast(new RaycastContext(player.getPos().add(new Vec3d(0, 1.5, 0)), fin, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, player));
    }
}
