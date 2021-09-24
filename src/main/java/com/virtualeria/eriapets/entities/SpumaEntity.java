package com.virtualeria.eriapets.entities;


import com.virtualeria.eriapets.Sounds.SoundsRegistry;
import com.virtualeria.eriapets.entities.ai.goals.PetFollowOwnerGoal;
import com.virtualeria.eriapets.entities.ai.goals.PetLookAtEntityGoal;

import com.virtualeria.eriapets.entities.ai.goals.SpumaPetExplosion;
import net.minecraft.block.Block;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;

import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.particle.ParticleTypes;

import net.minecraft.server.world.ServerWorld;

import net.minecraft.text.LiteralText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;

import software.bernie.geckolib3.core.event.ParticleKeyFrameEvent;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;


import java.util.EnumSet;

public class SpumaEntity extends BasePetEntity {
    public static final String petName = "spuma";
    boolean walk = false;

    BlockPos targetExplosionPos;
    boolean abilityAnimRunning = false;


    public BlockPos getTargetExplosionPos() {
        return targetExplosionPos;
    }

    public SpumaEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new SpumaEntity.SlimeMoveControl(this);
    }


    @Override
    public void registerControllers(AnimationData data) {
        AnimationController controller = new AnimationController(this, "controller", 0.0F, this::predicate);
        controller.registerParticleListener(this::particleListener);
        controller.registerSoundListener(this::soundListener);

        data.addAnimationController(controller);
    }

    private <ENTITY extends IAnimatable> void particleListener(ParticleKeyFrameEvent<ENTITY> event) {

        for (int i = 0; i < 40; i++)
            MinecraftClient.getInstance().particleManager.addParticle(
                    ParticleTypes.CLOUD, getX(), getY() + 1.2, getZ(),
                    0.0D, i * 0.03, 0.0D
            );
        float radio = 4;
        double x = 0;
        double y = 0;

        for (int h = 0; h < 360; h += 10) {
            x = Math.cos(h) * radio;
            y = Math.sin(h) * radio;

            Vec3d cirPos = new Vec3d(getX() + x, getY() + 1, getZ() + y).subtract(getPos()).normalize();

            MinecraftClient.getInstance().particleManager.addParticle(
                    ParticleTypes.CLOUD, getX(), getY(), getZ(),
                    cirPos.x * 0.3, 0.1, cirPos.z * 0.3
            );
            MinecraftClient.getInstance().particleManager.addParticle(
                    ParticleTypes.CLOUD, getX(), getY() + 0.3, getZ(),
                    getX() + x, 0, getZ() + y
            );
        }

        double yOff = 0;

        for (int i = 0; i < 5; i++) {
            for (int h = 0; h < 360; h += 5) {
                x = Math.cos(h) * radio;
                y = Math.sin(h) * radio;
                MinecraftClient.getInstance().particleManager.addParticle(
                        ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, getX() + x, getY() + yOff, getZ() + y,
                        x * 0.2f, 0.0D, y * 0.2f
                );
            }
            radio -= 0.5f;
            yOff += 0.5f;
        }
    }

    private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
        if (event.sound.equals("finish")) {
            this.abilityAnimRunning = false;
        } else if (event.sound.equals("explosion")) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            player.playSound(SoundsRegistry.EXPLOSIONBIG_EVENT, 0.1f, 1);
        } else {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            player.playSound(SoundsRegistry.SPUMACOCA_EVENT, 1, 1);
        }
    }

    public void setAbilityAnimRunning(boolean abilityIsrunning) {
        this.abilityAnimRunning = abilityIsrunning;
    }

    public void playAbilityAnim(World world) {
        setAbilityAnimRunning(true);
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        PlayState playState = PlayState.CONTINUE;

        if (this.abilityAnimRunning) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ability", false));
            return playState;
        }

        if (!this.isOnGround() && this.getCustomDeath() == 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.walk", false));
        } else if (this.getCustomDeath() > 0) {
            if (this.getCustomDeath() == 2 && event.getController().getAnimationState() == AnimationState.Stopped) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.deathPos", true));
            } else if (this.getCustomDeath() == 1) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.death", false));
                this.setCustomDeath(2);
            }
        }
        if (event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.idle", true));
        }
        return playState;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SpumaEntity.SwimmingGoal(this));
        this.goalSelector.add(2, new SpumaPetExplosion(this, (ServerWorld) world));
        this.goalSelector.add(3, new SpumaEntity.FaceTowardTargetGoal(this));
        this.goalSelector.add(5, new PetLookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        // this.goalSelector.add(4, new PetWanderAroundGoal(this, 1f));
        this.goalSelector.add(4, new PetFollowOwnerGoal(this, 1f, 3f, 8, false));

    }


    @Override
    public void customAbility() {
        PlayerEntity player = (PlayerEntity) this.getOwner();
        Vec3d playerDirection = player.getRotationVector().normalize();
        Vec3d fin = player.getPos().add(new Vec3d(0, 1.5, 0)).add(playerDirection.multiply(30));
        BlockHitResult blockHitResult = this.world.raycast(new RaycastContext(player.getPos().add(new Vec3d(0, 1.5, 0)), fin, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, player));

        if (blockHitResult.getType() != HitResult.Type.MISS) {

          //  world.setBlockState(blockHitResult.getBlockPos(), Blocks.EMERALD_BLOCK.getDefaultState(), Block.NOTIFY_ALL);

            this.getNavigation().isValidPosition(blockHitResult.getBlockPos());
            if (this.getNavigation().isValidPosition(blockHitResult.getBlockPos())) {
                targetExplosionPos = blockHitResult.getBlockPos();
                this.setAbilityRunning(true);
            } else {
                if (world.isClient)
                    ((PlayerEntity) this.getOwner()).sendMessage(new LiteralText("Spuma no puede llegar a esa posicion"), false);

            }

        }

    }

    protected int getTicksUntilNextJump() {
        return this.random.nextInt(20) + 10;
    }

    public void setTargetExplosionPos(BlockPos blockPos) {
        this.targetExplosionPos = blockPos;
    }

    private static class FaceTowardTargetGoal extends Goal {
        private final SpumaEntity slime;
        private int ticksLeft;

        public FaceTowardTargetGoal(SpumaEntity slime) {
            this.slime = slime;
            this.setControls(EnumSet.of(Goal.Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.slime.getOwner();
            if (livingEntity == null) {
                return false;
            } else if (!slime.isAlive()) {
                return false;
            } else if (slime.isAbilityRunning()) {
                return true;
            }
            return true;
        }

        public void start() {
            this.ticksLeft = 300;
            super.start();
        }

        public boolean shouldContinue() {
            LivingEntity livingEntity = this.slime.getOwner();
            if (!slime.isAlive()) {
                return false;
            } else if (livingEntity != null) {
                return true;
            } else if (slime.isAbilityRunning()) {
                return true;
            }
            return false;
        }

        public void tick() {
            if (this.slime.getNavigation().getTargetPos() != null && slime.isAbilityRunning()) {
                this.slime.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, new Vec3d(this.slime.getTargetExplosionPos().getX(), this.slime.getTargetExplosionPos().getY(), this.slime.getTargetExplosionPos().getZ()));
            } else this.slime.lookAtEntity(this.slime.getOwner(), 10.0F, 10.0F);

            ((SpumaEntity.SlimeMoveControl) this.slime.getMoveControl()).look(this.slime.getYaw(), true);
        }

    }

    private static class SwimmingGoal extends Goal {
        private final SpumaEntity slime;

        public SwimmingGoal(SpumaEntity slime) {
            this.slime = slime;
            this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
            slime.getNavigation().setCanSwim(true);
        }

        public boolean canStart() {
            return (this.slime.isTouchingWater() || this.slime.isInLava()) && this.slime.getMoveControl() instanceof SpumaEntity.SlimeMoveControl;
        }

        public void tick() {
            if (this.slime.getRandom().nextFloat() < 0.8F) {
                this.slime.getJumpControl().setActive();
            }

            ((SpumaEntity.SlimeMoveControl) this.slime.getMoveControl()).move(1.2D);
        }
    }

   /* @Override

    public double getJumpBoostVelocityModifier() {
        if (this.isAbilityRunning()) {
            return 0.25;
        } else {
            return (this.hasStatusEffect(StatusEffects.JUMP_BOOST) ? (double) (0.1F * (float) (this.getStatusEffect(StatusEffects.JUMP_BOOST).getAmplifier() + 1)) : 0.0D) + 0.05;
        }

    }*/

    private static class SlimeMoveControl extends MoveControl {
        private float targetYaw;
        private int ticksUntilJump;
        private final SpumaEntity slime;
        private boolean jumpOften;
        private int lateJump = 0;

        public SlimeMoveControl(SpumaEntity slime) {
            super(slime);
            this.slime = slime;
            this.targetYaw = 180.0F * slime.getYaw() / 3.1415927F;
        }


        public void look(float targetYaw, boolean jumpOften) {
            this.targetYaw = targetYaw;
            this.jumpOften = jumpOften;
        }

        public void move(double speed) {
            this.speed = speed;
            this.state = MoveControl.State.MOVE_TO;
        }

        public void tick() {
            this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), this.targetYaw, 90.0F));
            this.entity.headYaw = this.entity.getYaw();
            this.entity.bodyYaw = this.entity.getYaw();

            if (this.state != MoveControl.State.MOVE_TO) {
                this.entity.setForwardSpeed(0.0F);
            } else {
                this.state = MoveControl.State.WAIT;
                if (this.entity.isOnGround()) {
                    this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                    if (this.ticksUntilJump-- <= 0) {
                        this.ticksUntilJump = this.slime.getTicksUntilNextJump();
                        if (this.jumpOften) {
                            this.ticksUntilJump /= 3;
                        }

                        this.slime.getJumpControl().setActive();
                        this.slime.walk = true;
                        System.out.println("JUMP");
                        lateJump++;
                    } else {
                        this.slime.sidewaysSpeed = 0.0F;
                        this.slime.forwardSpeed = 0.0F;
                        this.entity.setMovementSpeed(0.0F);
                    }
                } else {
                    this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                }
            }
            if (lateJump != 0) {
                lateJump++;
                this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                if (lateJump == 4) lateJump = 0;
            }

        }
    }
}
