package com.virtualeria.eriapets.entities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePetEntity extends TameableEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    private static final TrackedData<Float> HUNGRY;
    private static final TrackedData<Float> HAPPINESS;
    private static final TrackedData<Integer> CUSTOMDEATH;
    private static final TrackedData<String> ABILITYUSETIME;

    private int abilityCooldown = 1;

    static {
        HUNGRY = DataTracker.registerData(BasePetEntity.class, TrackedDataHandlerRegistry.FLOAT);
        HAPPINESS = DataTracker.registerData(BasePetEntity.class, TrackedDataHandlerRegistry.FLOAT);
        CUSTOMDEATH =  DataTracker.registerData(BasePetEntity.class, TrackedDataHandlerRegistry.INTEGER);
        ABILITYUSETIME = DataTracker.registerData(BasePetEntity.class, TrackedDataHandlerRegistry.STRING);

    }
    public BasePetEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;


    }
    /**
     * Initialize sync data
     */
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HUNGRY, (Float)100f);
        this.dataTracker.startTracking(HAPPINESS, (Float)100f);
        this.dataTracker.startTracking(CUSTOMDEATH, (Integer)0);
        this.dataTracker.startTracking(ABILITYUSETIME, (String)"0");
    }

    protected void initGoals() {
            super.initGoals();
            customGoalsInit();
    }

    private void customGoalsInit(){
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(4, new WanderAroundGoal(this, 0.3f));
        this.goalSelector.add(3, new FollowOwnerGoal(this,1f, 3f, 8, false));
    }

    /**
     *  Save the hungry date to the world data
     *  * @param nbt
     */
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat("Hungry", this.getHungry());
        nbt.putFloat("Happiness", this.getHungry());
        nbt.putInt("CustomDeath", this.getCustomDeath());
    }
    /**
     *  Reads hungry date from the world and loads to the dataTrackers to sync the server
     *  * @param nbt
     */
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Hungry", 99)) {
            this.setHungry(nbt.getFloat("Hungry"));
        }
        if (nbt.contains("Happiness", 99)) {
            this.setHungry(nbt.getFloat("Happiness"));
        }
        if(nbt.contains("CustomDeath",99)){
            this.setCustomDeath(nbt.getInt("CustomDeath"));
            if(this.getCustomDeath() > 1) customDeathEvent();
        }
    }

    /**
     * Animation controller
     * @param event
     * @param <E>
     * @return
     */
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        PlayState playState = PlayState.CONTINUE;
        if (!(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F) && this.getCustomDeath() == 0 ) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.walk", false));
        } else if(this.getCustomDeath() > 0){
            if(this.getCustomDeath() == 2 && event.getController().getAnimationState() == AnimationState.Stopped){
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.deathPos", true));
            }else if(this.getCustomDeath() == 1){
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.death", false));
                this.setCustomDeath(2);
            }
        } if(event.getController().getAnimationState() == AnimationState.Stopped){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.idle", true));
        }
        return playState;
    }


    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }

    @Override
    public void mobTick(){
        setHungry(this.getHungry() - 0.003f);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.GOLD_INGOT;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {

        ItemStack itemStack = player.getStackInHand(hand);
        if (isBreedingItem(itemStack)) {
            int i = getBreedingAge();
            if (!world.isClient && i == 0 && canEat()) {
                eat(player, hand, itemStack);
                lovePlayer(player);
                System.out.println("SET OWNER " + player);
                setOwner(player);
                return ActionResult.SUCCESS;
            }
            if (world.isClient) {
                return ActionResult.CONSUME;
            }
        }

            if(!itemStack.isEmpty() && itemStack.getItem() == Items.DIAMOND_AXE){
                drawGUI();
                return ActionResult.SUCCESS;
            }else{
                if(this.getCustomDeath() == 0)   {
                    customAbility();
                    return ActionResult.SUCCESS;
                }else if(this.getCustomDeath() != 0) revive();
            }


        return super.interactMob(player, hand);
    }

    @Override
    public void onDeath(DamageSource source) {
        if(!source.isOutOfWorld()){
            customDeathEvent();
        }else{
            this.setInvulnerable(false);
            super.onDeath(source);
        }
    }

    public void drawFireEffect(){
        if(world.isClient){
        double x = 0;
        double y = 0;
        double radio = 10;
        double yOff = 0;

        for(int i = 0; i < 50 ;i++){
            for(int h = 0 ; h < 360;h += 5){
                x = Math.cos(h) * radio;
                y = Math.sin(h) * radio;
                MinecraftClient.getInstance().particleManager.addParticle(
                        ParticleTypes.FLAME, getX() + x, getY() + yOff, getZ() + y,
                        0.0D, 0.0D, 0.0D
                );
            }
            yOff += 0.3f;
            radio -=0.5f;
            if(radio <= 0) break;
        } }
    }

    public void customDeathEvent(){
        this.setInvulnerable(true);
        this.setHealth(1);
        this.setCustomDeath(1);
        this.goalSelector.clear();

    }

    public void customAbility(){
            float radio = 10;
            List<Entity> listEntities = world.getOtherEntities((Entity)null,new Box(getX() - radio, getY() - radio, getZ() - radio, getX()  + radio, getY()  + radio, getZ() + radio));

            for (Entity entidad: listEntities ) {
                if(entidad.getType() == EntityType.ZOMBIE || entidad.getType() == EntityType.SLIME){
                    entidad.kill();
                }
            }
            drawFireEffect();
    }


    /**
     * Draws de GUI of the pet
     */
    public void drawGUI(){
        System.out.println("[BasePet] drawGUI");
    }

    public void setHungry(float v){
        this.dataTracker.set(HUNGRY,v);
    }
    public float getHungry(){
        return (Float)this.dataTracker.get(HUNGRY);
    }
    public void setHappiness(float v){
        this.dataTracker.set(HAPPINESS,v);
    }
    public float getHappiness(){
        return (Float)this.dataTracker.get(HAPPINESS);
    }
    public void setCustomDeath(Integer v){
        this.dataTracker.set(CUSTOMDEATH,v);
    }
    public Integer getCustomDeath(){
        return (Integer)this.dataTracker.get(CUSTOMDEATH);
    }

    public boolean abilityIsCooledDown(){
        return new Timestamp(System.currentTimeMillis()).getTime() > this.getAbilityUsedTime();
    }
    public Long getAbilityUsedTime(){
        String x = (String)this.dataTracker.get(ABILITYUSETIME);
        return Long.parseLong(x);
    }
    public void setAbilityUsedTime(Long abilityUsedTime){
        this.dataTracker.set(ABILITYUSETIME,abilityUsedTime.toString());
    }
    public void useAbility(){
        setAbilityUsedTime(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(abilityCooldown));
    }

    public void revive(){
        this.customGoalsInit();
        this.setCustomDeath(0);
        this.setInvulnerable(false);
    }




}
