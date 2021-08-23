package com.virtualeria.eriapets.access;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;

public interface PlayerInventoryDuck {

    ItemStack getFirstTagItemSlotId(Tag<Item> tag);
}
