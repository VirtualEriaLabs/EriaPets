package com.virtualeria.eriapets.gui;

import com.virtualeria.eriapets.entities.BasePetEntity;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTabPanel;
import io.github.cottonmc.cotton.gui.widget.data.Color;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;


public class PetGui extends LightweightGuiDescription {
    public PetGui(BasePetEntity petEntity) {
        int gridSize = 18;
        int gridLength = 9;
        int gridHeight = 12;

        Insets tabPanelInsets = new Insets(0);

        WTabPanel root = new WTabPanel();
        setRootPanel(root);

        //Main Panel
        WGridPanel petPanel = new WGridPanel(gridSize);
        petPanel.setInsets(new Insets(7));
        petPanel.setSize(gridLength * gridSize, gridHeight * gridSize);

        WGridPanel petView = new WGridPanel(gridSize);
        petView.setInsets(new Insets(0));
        petView.setBackgroundPainter(BackgroundPainter.createColorful(Color.BLACK.toRgb(), 0));
        petPanel.add(petView, 2, 0, 4, 5);

        petPanel.add(new WLabel(new LiteralText("Health: " + petEntity.getHealth()).formatted(Formatting.BOLD)), 0, 5);
        petPanel.add(new WLabel(new LiteralText("Hunger: " + petEntity.getHungry()).formatted(Formatting.BOLD)), 0, 6);

        WLabel petName = new WLabel(new LiteralText(petEntity.getDisplayName().asString()).formatted(Formatting.BOLD));
        petName.setColor(Color.LIGHT_BLUE_DYE.toRgb());
        petName.setHorizontalAlignment(HorizontalAlignment.CENTER);
        petName.setVerticalAlignment(VerticalAlignment.CENTER);
        petPanel.add(petName, 2, 5, 4, 1);

        //Health or Stats Panel
        WGridPanel healthPanel = new WGridPanel(3);
        healthPanel.setInsets(tabPanelInsets);

        //Inventory
        WGridPanel inventoryPanel = new WGridPanel(3);
        inventoryPanel.setBackgroundPainter(BackgroundPainter.createColorful(Color.WHITE.toRgb()));
        inventoryPanel.setInsets(tabPanelInsets);
        inventoryPanel.add(new WLabel(new LiteralText("Inventory").formatted(Formatting.BOLD)), 0, 0, 1, 1);

        //Add panels to tab panel
        root.add(petPanel, tab -> tab.icon(new ItemIcon(new ItemStack(Items.NAME_TAG))).tooltip(new LiteralText("Your Pet")));
        root.add(healthPanel, tab -> tab.icon(new ItemIcon(new ItemStack(Items.APPLE))).tooltip(new LiteralText("Health")));
        root.add(inventoryPanel, tab -> tab.icon(new ItemIcon(new ItemStack(Items.CHEST))).tooltip(new LiteralText("Inventory")));

        root.validate(this);
    }

}
