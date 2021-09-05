package com.virtualeria.eriapets.entities.inv;

import com.virtualeria.eriapets.entities.BasePetEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class PetEntityInventory implements Inventory {
    private final BasePetEntity petEntity;
    private final int size;
    private final DefaultedList<ItemStack> stacks;

    public PetEntityInventory(BasePetEntity petEntity){
        this.petEntity = petEntity;
        this.size = petEntity.getInventorySize()+petEntity.getEquipmentSize();
        this.stacks = DefaultedList.ofSize(size,ItemStack.EMPTY);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size; i++) {
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return stacks.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(stacks, slot, amount);
        if (!result.isEmpty()) {
            markDirty();
        }

        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(stacks, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        stacks.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
    }

    @Override
    public void markDirty() {
        //TODO Dynamically change values, like armor if you equip armor (?)
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.stacks.clear();
    }

    public void dropInventory(){
        //Don't drop equipment/upgrades
        for(int i = petEntity.getEquipmentSize(); i < stacks.size(); i++){
            petEntity.dropStack(stacks.get(i));
            stacks.set(i,ItemStack.EMPTY);
        }
    }
}
