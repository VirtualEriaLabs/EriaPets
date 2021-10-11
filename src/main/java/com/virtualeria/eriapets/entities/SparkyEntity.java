package com.virtualeria.eriapets.entities;

import com.virtualeria.eriapets.client.particles.ParticlesRegistry;
import com.virtualeria.eriapets.entities.ai.goals.KeepEntityAboveOwnerGoal;
import com.virtualeria.eriapets.entities.ai.goals.PetFollowOwnerGoal;
import com.virtualeria.eriapets.entities.ai.goals.PetLookAtEntityGoal;
import com.virtualeria.eriapets.entities.ai.goals.SparkyElectrocuteEntities;
import com.virtualeria.eriapets.entities.ai.task.FlyWanderAround;
import com.virtualeria.eriapets.utils.RaycastUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.LiteralText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SparkyEntity extends BasePetEntity {
    public static final String petName = "sparky";

    BlockPos targetElectrocute;
    private static final int ELECTROCUTE_RADIUS = 5;

    public boolean isShowElectrocuteParticles() {
        return showElectrocuteParticles;
    }

    public void setShowElectrocuteParticles(boolean showElectrocuteParticles) {
        this.showElectrocuteParticles = showElectrocuteParticles;
    }

    private boolean showElectrocuteParticles = false;

    public BlockPos getTargetElectrocute() {
        return targetElectrocute;
    }

    public void setTargetElectrocute(BlockPos targetElectrocute) {
        this.targetElectrocute = targetElectrocute;
    }


    public SparkyEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 5, true);
    }

    @Override
    public void customAbility() {
        PlayerEntity player = (PlayerEntity) this.getOwner();

        BlockHitResult blockHitResult = RaycastUtils.raycastFromPlayerView(player, 30, this.world);
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            this.getNavigation().isValidPosition(blockHitResult.getBlockPos());
            if (this.getNavigation().isValidPosition(blockHitResult.getBlockPos())) {
                this.setAbilityRunning(true);
                this.setTargetElectrocute(blockHitResult.getBlockPos());
            } else {
                if (world.isClient)
                    ((PlayerEntity) this.getOwner()).sendMessage(new LiteralText("Sparky no puede llegar a esa posicion"), false);
            }
        }
    }

    public static DefaultAttributeContainer.Builder createSparkyAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D).add(EntityAttributes.GENERIC_FLYING_SPEED, 1).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20000000298023224D);
    }

    @Override
    public void customGoalsInit() {
        this.goalSelector.add(1, new SparkyElectrocuteEntities(this, world,ELECTROCUTE_RADIUS));
        this.goalSelector.add(2, new KeepEntityAboveOwnerGoal(this));
        this.goalSelector.add(3, new PetLookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new PetFollowOwnerGoal(this, 1f, 3f, 8, false));
        this.goalSelector.add(5, new FlyWanderAround(this));
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public void updateVelocity(float speed, Vec3d movementInput) {
        Vec3d vec3d = movementInputToVelocity(movementInput, speed * 2.5f, this.getYaw());
        this.setVelocity(this.getVelocity().add(vec3d));
    }

    private static Vec3d movementInputToVelocity(Vec3d movementInput, float speed, float yaw) {
        double d = movementInput.lengthSquared();
        if (d < 1.0E-7D) {
            return Vec3d.ZERO;
        } else {
            Vec3d vec3d = (d > 1.0D ? movementInput.normalize() : movementInput).multiply((double) speed);
            float f = MathHelper.sin(yaw * 0.017453292F);
            float g = MathHelper.cos(yaw * 0.017453292F);
            return new Vec3d(vec3d.x * (double) g - vec3d.z * (double) f, vec3d.y, vec3d.z * (double) g + vec3d.x * (double) f);
        }
    }

    @Override
    public void mobTick() {
        super.mobTick();
    }

    public void drawElectricParticles(){
        for (float z = targetElectrocute.getZ() + ELECTROCUTE_RADIUS; z > targetElectrocute.getZ() - ELECTROCUTE_RADIUS; z--) {
            for (float x = (targetElectrocute.getX() + ELECTROCUTE_RADIUS); x > targetElectrocute.getX() - ELECTROCUTE_RADIUS; x--) {
                for (float y = (targetElectrocute.getY() + ELECTROCUTE_RADIUS); y > targetElectrocute.getY() - ELECTROCUTE_RADIUS; y--) {
                    BlockState blockstate = world.getBlockState(new BlockPos(x, y, z));
                    BlockState upperBlock = world.getBlockState(new BlockPos(x, y + 1, z));
                    if (!blockstate.isAir() && upperBlock.isAir())
                        MinecraftClient.getInstance().particleManager.addParticle(
                               ParticlesRegistry.LIGHTING_PARTICLE, x+ 0.1f, y + 1.5, z+ 0.1f,
                                0.0D, 0.05, 0.0D
                        );
                }
            }
        }
    }


    private void drawAbilityParticle(){
        double x = 0;
        double y = 0;
        double radio = 2;
        double yOff = 0;

        for (int i = 0; i < 5; i++) {
            for (int h = 0; h < 360; h += 5) {
                x = Math.cos(h) * radio;
                y = Math.sin(h) * radio;
                MinecraftClient.getInstance().particleManager.addParticle(
                        ParticlesRegistry.LIGHTING_PARTICLE, getX() + x, getY() + yOff, getZ() + y,
                        x *0.5f,0.0D, y*0.5f
                );
            }
            yOff += 0.3f;
        }
    }

    public void drawPetEffects(World world,BlockPos pos) {
        if(world.isClient){
            this.setTargetElectrocute(pos);
            drawElectricParticles();
        }
    }

}
