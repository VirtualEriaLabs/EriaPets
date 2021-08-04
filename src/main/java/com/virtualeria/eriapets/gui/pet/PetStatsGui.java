package com.virtualeria.eriapets.gui.pet;

import static com.virtualeria.eriapets.gui.pet.PetStatsGui.StatsPanelValues.STATS_GRID_PANEL_SIZE;

import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;

public class PetStatsGui {
  public static WGridPanel buildStatsPanelGrid() {
    WGridPanel statsPanelGrid = new WGridPanel(STATS_GRID_PANEL_SIZE);
    statsPanelGrid.setInsets(TAB_PANEL_INSETS);
    return statsPanelGrid;
  }


  public static class StatsPanelValues {
    public static final int STATS_GRID_PANEL_SIZE = 3;
    public static final ItemIcon STATS_PANEL_TAB_ICON = new ItemIcon(new ItemStack(Items.APPLE));
    public static final String STATS_PANEL_TAB_TOOLTIP = "Health";
  }
}
