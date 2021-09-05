package com.virtualeria.eriapets.client.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class PetScreen extends CottonInventoryScreen<PetGuiDescription> {
    public PetScreen(PetGuiDescription description, PlayerEntity player, Text title) {
        super(description, player);
    }
}
