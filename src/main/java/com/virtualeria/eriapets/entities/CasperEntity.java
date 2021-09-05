package com.virtualeria.eriapets.entities;

import com.virtualeria.eriapets.access.EntityDuck;
import com.virtualeria.eriapets.entities.ai.task.FlyWanderAround;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;

import net.minecraft.entity.passive.TameableEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class CasperEntity extends BasePetEntity {
  public static final String petName = "casper";

  List<Entity> glowingEntities;

  private int casperAbilityDuration = 10000;
  private float abilityRadio = 10;



  public CasperEntity(EntityType<? extends TameableEntity> entityType, World world)
  {
    super(entityType, world);
    this.moveControl = new FlightMoveControl(this, 360, true);

  }

  @Override
  public void customAbility() {
    glowingEntities = world.getOtherEntities((Entity) null, new Box(getX() - abilityRadio, getY() - abilityRadio, getZ() - abilityRadio, getX() + abilityRadio, getY() + abilityRadio, getZ() + abilityRadio));
      if(world.isClient){
        drawAbilityParticle();
        for (Entity entity:
                glowingEntities)
          ((EntityDuck)entity).setLocalGlowing(true);

        new Timer().schedule(new TimerTask() {
          @Override
          public void run() {
            for (Entity entity:
                    glowingEntities)
              ((EntityDuck)entity).setLocalGlowing(false);
          }
        }, casperAbilityDuration);

      }

      this.useAbility();
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
                ParticleTypes.CLOUD, getX() + x, getY() + yOff, getZ() + y,
                x *0.5f,0.0D, y*0.5f
        );
      }
      yOff += 0.3f;
    }
  }
  public static DefaultAttributeContainer.Builder createCasperAttributes() {
    return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D).add(EntityAttributes.GENERIC_FLYING_SPEED, 1).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20000000298023224D);
  }

  @Override
  public void customGoalsInit() {
    this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
    this.goalSelector.add(3, new FollowOwnerGoal(this, 1f, 3f, 8, false));
    this.goalSelector.add(4, new FlyWanderAround(this));
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
      Vec3d vec3d = (d > 1.0D ? movementInput.normalize() : movementInput).multiply((double)speed);
      float f = MathHelper.sin(yaw * 0.017453292F);
      float g = MathHelper.cos(yaw * 0.017453292F);
      return new Vec3d(vec3d.x * (double)g - vec3d.z * (double)f, vec3d.y, vec3d.z * (double)g + vec3d.x * (double)f);
    }
  }


}
