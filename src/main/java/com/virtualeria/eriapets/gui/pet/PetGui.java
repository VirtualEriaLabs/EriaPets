package com.virtualeria.eriapets.gui.pet;

import static com.virtualeria.eriapets.gui.pet.PetGui.InventoryPanelValues.INVENTORY_GRID_PANEL_SIZE;
import static com.virtualeria.eriapets.gui.pet.PetGui.InventoryPanelValues.INVENTORY_PANEL_TAB_ICON;
import static com.virtualeria.eriapets.gui.pet.PetGui.InventoryPanelValues.INVENTORY_PANEL_TAB_TOOLTIP;
import static com.virtualeria.eriapets.gui.pet.PetGui.InventoryPanelValues.INVENTORY_TITLE;
import static com.virtualeria.eriapets.gui.pet.PetGui.InventoryPanelValues.INVENTORY_TITLE_LABEL_HEIGHT;
import static com.virtualeria.eriapets.gui.pet.PetGui.InventoryPanelValues.INVENTORY_TITLE_LABEL_WIDTH;
import static com.virtualeria.eriapets.gui.pet.PetGui.InventoryPanelValues.INVENTORY_TITLE_LABEL_X;
import static com.virtualeria.eriapets.gui.pet.PetGui.InventoryPanelValues.INVENTORY_TITLE_LABEL_Y;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.HEALTH_LABEL_X;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.HEALTH_LABEL_Y;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.HUNGER_LABEL_X;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.HUNGER_LABEL_Y;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_GRID_HEIGHT;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_GRID_LENGTH;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_GRID_SIZE;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_HEALTH_LABEL;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_HUNGER_LABEL;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_NAME_LABEL_HEIGHT;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_NAME_LABEL_WIDTH;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_NAME_LABEL_X;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_NAME_LABEL_Y;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_PANEL_TAB_ICON;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_PANEL_TAB_TOOLTIP;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_VIEW_HEIGHT;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_VIEW_WIDTH;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_VIEW_X;
import static com.virtualeria.eriapets.gui.pet.PetGui.PetPanelValues.PET_VIEW_Y;
import static com.virtualeria.eriapets.gui.pet.PetGui.StatsPanelValues.STATS_PANEL_TAB_ICON;
import static com.virtualeria.eriapets.gui.pet.PetGui.StatsPanelValues.STATS_PANEL_TAB_TOOLTIP;

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
    WTabPanel root = new WTabPanel();
    setRootPanel(root);

    root.add(buildPetPanelGrid(petEntity),
        tab -> tab.icon(PET_PANEL_TAB_ICON).tooltip(new LiteralText(PET_PANEL_TAB_TOOLTIP)));

    root.add(buildStatsPanelGrid(),
        tab -> tab.icon(STATS_PANEL_TAB_ICON).tooltip(new LiteralText(STATS_PANEL_TAB_TOOLTIP)));

    root.add(buildInventoryPanelGrid(), tab -> tab.icon(INVENTORY_PANEL_TAB_ICON)
        .tooltip(new LiteralText(INVENTORY_PANEL_TAB_TOOLTIP)));

    root.validate(this);
  }

  private static WGridPanel buildPetPanelGrid(BasePetEntity petEntity) {
    WGridPanel petPanel = new WGridPanel(PET_GRID_SIZE);
    petPanel.setInsets(new Insets(7));
    petPanel.setSize(PET_GRID_LENGTH * PET_GRID_SIZE, PET_GRID_HEIGHT * PET_GRID_SIZE);
    petPanel.add(buildPetView(),
        PET_VIEW_X,
        PET_VIEW_Y,
        PET_VIEW_WIDTH,
        PET_VIEW_HEIGHT);
    petPanel.add(buildHealthLabel(petEntity), HEALTH_LABEL_X, HEALTH_LABEL_Y);
    petPanel.add(buildHungerLabel(petEntity), HUNGER_LABEL_X, HUNGER_LABEL_Y);
    petPanel
        .add(buildPetNameLabel(petEntity), PET_NAME_LABEL_X, PET_NAME_LABEL_Y, PET_NAME_LABEL_WIDTH,
            PET_NAME_LABEL_HEIGHT);
    return petPanel;
  }

  private static WGridPanel buildInventoryPanelGrid() {
    WGridPanel inventoryPanelGrid = new WGridPanel(INVENTORY_GRID_PANEL_SIZE);
    inventoryPanelGrid.setBackgroundPainter(BackgroundPainter.createColorful(Color.WHITE.toRgb()));
    inventoryPanelGrid.setInsets(TAB_PANEL_INSETS);
    inventoryPanelGrid.add(buildInventoryLabel(),
        INVENTORY_TITLE_LABEL_X,
        INVENTORY_TITLE_LABEL_Y,
        INVENTORY_TITLE_LABEL_WIDTH,
        INVENTORY_TITLE_LABEL_HEIGHT);
    return inventoryPanelGrid;
  }

  private static WGridPanel buildPetView() {
    WGridPanel petView = new WGridPanel(PET_GRID_SIZE);
    petView.setInsets(new Insets(0));
    petView.setBackgroundPainter(BackgroundPainter.createColorful(Color.BLACK.toRgb(), 0));
    return petView;
  }

  private static WLabel buildPetNameLabel(BasePetEntity petEntity) {
    WLabel petNameLabel = new WLabel(
        new LiteralText(petEntity.getDisplayName().asString()).formatted(Formatting.BOLD));
    petNameLabel.setColor(Color.LIGHT_BLUE_DYE.toRgb());
    petNameLabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
    petNameLabel.setVerticalAlignment(VerticalAlignment.CENTER);
    return petNameLabel;
  }

  private static WLabel buildInventoryLabel() {
    return buildLabel(INVENTORY_TITLE, Formatting.BOLD);
  }

  private static WLabel buildHealthLabel(BasePetEntity petEntity) {
    return buildLabel(String.format(PET_HEALTH_LABEL, petEntity.getHealth()), Formatting.BOLD);
  }

  private static WLabel buildHungerLabel(BasePetEntity petEntity) {
    return buildLabel(String.format(PET_HUNGER_LABEL, petEntity.getHungry()), Formatting.BOLD);
  }

  private static WLabel buildLabel(String label, Formatting format) {
    return new WLabel(new LiteralText(label).formatted(format));
  }

  public static class PetPanelValues {
    public static final int PET_GRID_SIZE = 18;
    public static final int PET_GRID_LENGTH = 9;
    public static final int PET_GRID_HEIGHT = 12;

    public static final int PET_VIEW_X = 2;
    public static final int PET_VIEW_Y = 0;
    public static final int PET_VIEW_WIDTH = 4;
    public static final int PET_VIEW_HEIGHT = 5;

    public static final int HEALTH_LABEL_X = 0;
    public static final int HEALTH_LABEL_Y = 6;

    public static final int HUNGER_LABEL_X = 0;
    public static final int HUNGER_LABEL_Y = 6;

    public static final int PET_NAME_LABEL_X = 2;
    public static final int PET_NAME_LABEL_Y = 5;
    public static final int PET_NAME_LABEL_WIDTH = 4;
    public static final int PET_NAME_LABEL_HEIGHT = 1;

    public static final String PET_HEALTH_LABEL = "Health: %f";
    public static final String PET_HUNGER_LABEL = "Hunger: %f";

    public static final ItemIcon PET_PANEL_TAB_ICON = new ItemIcon(new ItemStack(Items.NAME_TAG));
    public static final String PET_PANEL_TAB_TOOLTIP = "Your Pet";
  }

  public static class InventoryPanelValues {
    public static final int INVENTORY_TITLE_LABEL_X = 0;
    public static final int INVENTORY_TITLE_LABEL_Y = 0;
    public static final int INVENTORY_TITLE_LABEL_WIDTH = 1;
    public static final int INVENTORY_TITLE_LABEL_HEIGHT = 1;
    public static final int INVENTORY_GRID_PANEL_SIZE = 3;

    public static final String INVENTORY_TITLE = "Inventory";
    public static final ItemIcon INVENTORY_PANEL_TAB_ICON =
        new ItemIcon(new ItemStack(Items.CHEST));
    public static final String INVENTORY_PANEL_TAB_TOOLTIP = "Inventory";
  }

  private static final Insets TAB_PANEL_INSETS = new Insets(0);
}
