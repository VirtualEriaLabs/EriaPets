package com.virtualeria.eriapets.client.gui;

import com.virtualeria.eriapets.EriaPetsMain;
import com.virtualeria.eriapets.entities.BasePetEntity;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class PetGuiDescription extends SyncedGuiDescription {

    private static final int PANEL_INSETS = 7;

    private static final int WINDOW_WIDTH = 176;
    private static final int WINDOW_HEIGHT = 170;

    private static final int PET_VIEW_WIDTH = 60;
    private static final int PET_VIEW_HEIGHT = 60;

    public PetGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext ctx, BasePetEntity petEntity) {
        super(EriaPetsMain.PET_SCREEN_HANDLER_TYPE, syncId, playerInventory);

        //Root tab panel
        WTabPanel root = new WTabPanel();
        setRootPanel(root);

        //Pet tab
        WPlainPanel petPanel = buildTabPanel(root, Items.NAME_TAG,"Your Pet");
        petPanel.add(buildLabel(petEntity.getDisplayName(), VerticalAlignment.CENTER, HorizontalAlignment.LEFT),0,10);
        WEntityViewer petViewer = new WEntityViewer(petEntity,40);
        petPanel.add(petViewer, WINDOW_WIDTH - PET_VIEW_WIDTH - PANEL_INSETS*2, 0, PET_VIEW_WIDTH, PET_VIEW_HEIGHT);

        //Stats tab
        WPlainPanel petStatsPanel = buildTabPanel(root,Items.APPLE,"Health");
        petStatsPanel.add(buildLabel("HP: " + Float.toString(petEntity.getHealth())),0,20);
        petStatsPanel.add(buildLabel("Hunger: " + Float.toString(petEntity.getHungry())),0,40);

        //Inventory tab
        WPlainPanel petInventoryPanel = buildTabPanel(root,Items.CHEST,"Pet Inventory");
        buildPetInventory(petEntity.getInventory(),petInventoryPanel);
        petInventoryPanel.add(this.createPlayerInventoryPanel(true),0,69);

        root.validate(this);
    }

    /**
     * Creates a new tab in a tab panel with an icon, tooltip and title.
     * @param tabPanel Main tab panel where new tab will be added.
     * @param itemIcon Tab icon
     * @param tabName Tab tooltip and name title
     * @return WPlainPanel
     */
    private WPlainPanel buildTabPanel(WTabPanel tabPanel, Item itemIcon, String tabName){
        WPlainPanel plainPanel = new WPlainPanel();
        plainPanel.setInsets(Insets.ROOT_PANEL);
        plainPanel.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);

        plainPanel.add(buildLabel(tabName),0,0);

        tabPanel.add(plainPanel, tab -> tab.icon(new ItemIcon(new ItemStack(itemIcon))).tooltip(new LiteralText(tabName)));

        return plainPanel;
    }

    private WLabel buildLabel(String text){
        return new WLabel(new LiteralText(text));
    }

    private WLabel buildLabel(Text text,VerticalAlignment vAlignment, HorizontalAlignment hAlignment){
        return new WLabel(text).setVerticalAlignment(vAlignment).setHorizontalAlignment(hAlignment);
    }

    private void buildPetInventory(Inventory inventory,WPlainPanel petInventoryPanel){
        int y = 11;
        int x = 0;
        for(int i = 0; i < inventory.size(); i++){
            WItemSlot itemSlot = WItemSlot.of(inventory, i);
            petInventoryPanel.add(itemSlot,x++*18,y,16,16);
            if(x != 0 && (x) % 9 == 0){
                y += 18;
                x = 0;
            }
        }
    }

    /**
     * This makes the root (tab) panel transparent
     */
    @Override
    public void addPainters() {}
}