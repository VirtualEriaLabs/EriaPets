package com.virtualeria.eriapets.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;

import net.minecraft.entity.passive.TameableEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;


public class CasperEntity extends BasePetEntity {
  public static final String petName = "casper";

  public CasperEntity(EntityType<? extends TameableEntity> entityType, World world)
  {
    super(entityType, world);
    this.moveControl = new FlightMoveControlTest(this, 365, true);
    this.setGlowing(true);
  }

  @Override
  public void customAbility() {
    //Don't do anything passive ability
    setGlowing(true);
  }
  public static DefaultAttributeContainer.Builder createCasperAttributes() {
    return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D).add(EntityAttributes.GENERIC_FLYING_SPEED, 10).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20000000298023224D);
  }

  @Override
  protected void initGoals() {
    this.goalSelector.add(0, new SwimGoal(this));
    this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
    this.goalSelector.add(2, new FollowOwnerGoal(this, 1, 2.0F, 3.0F, true));
  }



  private class CasperWanderAroundGoal extends Goal {
    private static final int field_30309 = 22;
    CasperEntity entity;
    CasperWanderAroundGoal(CasperEntity entity) {
      this.setControls(EnumSet.of(Goal.Control.MOVE));
      this.entity = entity;
    }

    public boolean canStart() {
      return CasperEntity.this.navigation.isIdle() && CasperEntity.this.random.nextInt(10) == 0;
    }

    public boolean shouldContinue() {
      return CasperEntity.this.navigation.isFollowingPath();
    }

    public void start() {

      Vec3d vec3d = this.getRandomLocation();
      if (vec3d != null) {
        CasperEntity.this.navigation.startMovingAlong(CasperEntity.this.navigation.findPathTo((BlockPos)(new BlockPos(vec3d)), 2), 1);
      }

    }

    @Nullable
    private Vec3d getRandomLocation() {
      Vec3d vec3d3;
      vec3d3 = CasperEntity.this.getRotationVec(0.0F);
      int i = 0;
      Vec3d vec3d4 = AboveGroundTargeting.find(CasperEntity.this, 8, 7, vec3d3.x, vec3d3.z, 1.5707964F, 3, 1);
      return vec3d4 != null ? vec3d4 : NoPenaltySolidTargeting.find(CasperEntity.this, 8, 4, -2, vec3d3.x, vec3d3.z, 1.5707963705062866D);
    }
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
  private class FlightMoveControlTest extends MoveControl {
    private final int maxPitchChange;
    private final boolean noGravity;

    public FlightMoveControlTest(MobEntity entity, int maxPitchChange, boolean noGravity) {
      super(entity);
      this.maxPitchChange = maxPitchChange;
      this.noGravity = noGravity;
    }

    public void tick() {
      if (this.state == MoveControl.State.MOVE_TO) {
        this.state = MoveControl.State.WAIT;
        this.entity.setNoGravity(true);
        double d = this.targetX - this.entity.getX();
        double e = this.targetY - this.entity.getY();
        double f = this.targetZ - this.entity.getZ();
        double g = d * d + e * e + f * f;
        if (g < 2.500000277905201E-7D) {
          this.entity.setUpwardSpeed(0.0F);
          this.entity.setForwardSpeed(0.0F);
          return;
        }

        float h = (float) (MathHelper.atan2(f, d) * 57.2957763671875D) - 90.0F;
        this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), h, 90.0F));
        float j;
        if (this.entity.isOnGround()) {
          j = (float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
        } else {
          j = (float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED));
        }

        this.entity.setMovementSpeed(j);
        double k = Math.sqrt(d * d + f * f);
        if (Math.abs(e) > 9.999999747378752E-6D || Math.abs(k) > 9.999999747378752E-6D) {
          float l = (float) (-(MathHelper.atan2(e, k) * 57.2957763671875D));
          this.entity.setPitch(this.wrapDegrees(this.entity.getPitch(), l, (float) this.maxPitchChange));
          this.entity.setUpwardSpeed(e > 0.0D ? j : -j);
        }
      } else {
        if (!this.noGravity) {
          this.entity.setNoGravity(false);
        }

        this.entity.setUpwardSpeed(0.0F);
        this.entity.setForwardSpeed(0.0F);
      }

      System.out.println("SPEED " + this.entity.upwardSpeed + "this.entity.fowardspeed  " +this.entity.forwardSpeed  );

    }
  }

}
