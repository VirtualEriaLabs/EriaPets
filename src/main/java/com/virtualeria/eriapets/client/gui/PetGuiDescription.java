package com.virtualeria.eriapets.client.gui;

import com.virtualeria.eriapets.EriaPetsMain;
import com.virtualeria.eriapets.entities.BasePetEntity;
import com.virtualeria.eriapets.utils.Constants;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PetGuiDescription extends SyncedGuiDescription {

    private static final int PANEL_INSETS = 7;

    private static final int WINDOW_WIDTH = 176;
    private static final int WINDOW_HEIGHT = 170;

    private static final int PET_VIEW_WIDTH = 60;
    private static final int PET_VIEW_HEIGHT = 60;

    private final BasePetEntity petEntity;

    public PetGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext ctx, BasePetEntity petEntity) {
        super(EriaPetsMain.PET_SCREEN_HANDLER_TYPE, syncId, playerInventory);
        this.petEntity = petEntity;

        //Root tab panel
        WTabPanel root = new WTabPanel();
        setRootPanel(root);

        //Pet tab
        WPlainPanel petPanel = buildTabPanel(root, Items.NAME_TAG,"Your Pet");
        petPanel.add(buildLabel(petEntity.getDisplayName(), VerticalAlignment.CENTER, HorizontalAlignment.LEFT),0,10);
        WEntityViewer petViewer = new WEntityViewer(petEntity,40);
        petPanel.add(petViewer, WINDOW_WIDTH - PET_VIEW_WIDTH - PANEL_INSETS*2, 0, PET_VIEW_WIDTH, PET_VIEW_HEIGHT);

        buildBar(petPanel,healthIcon, healthBar,0,1,0,70);
        buildBar(petPanel,hungerIcon, hungerBar,2,3,0,82);
        buildBar(petPanel,happinessIcon, happinessBar,4,5,0,94);

        buildPetEquipment(petPanel,WINDOW_WIDTH-18*2-PANEL_INSETS*2,70);

        //Inventory tab
        WPlainPanel petInventoryPanel = buildTabPanel(root,Items.CHEST,"Pet Inventory");
        buildPetInventory(petEntity.getInventory(),petInventoryPanel);
        petInventoryPanel.add(this.createPlayerInventoryPanel(true),0,69);

        root.validate(this);
    }

    private static final Texture barBg = new Texture(new Identifier(Constants.ModID,"textures/gui/barbg.png"));
    private static final Texture healthBar = new Texture(new Identifier(Constants.ModID,"textures/gui/healthbar.png"));
    private static final Texture hungerBar = new Texture(new Identifier(Constants.ModID,"textures/gui/hungerbar.png"));
    private static final Texture happinessBar = new Texture(new Identifier(Constants.ModID,"textures/gui/happinessbar.png"));

    private static final Texture healthIcon = new Texture(new Identifier(Constants.ModID,"textures/gui/healthicon.png"));
    private static final Texture hungerIcon = new Texture(new Identifier(Constants.ModID,"textures/gui/hungericon.png"));
    private static final Texture happinessIcon = new Texture(new Identifier(Constants.ModID,"textures/gui/happinessicon.png"));

    private static final Texture swordIcon = new Texture(new Identifier(Constants.ModID,"textures/gui/swordslot.png"));
    private static final Texture armorIcon = new Texture(new Identifier(Constants.ModID,"textures/gui/armorslot.png"));
    private static final Texture upgradeIcon = new Texture(new Identifier(Constants.ModID,"textures/gui/upgradeslot.png"));

    private void buildBar(WPlainPanel panel, Texture iconTexture,Texture progressTexture, int value, int max, int x, int y){
        WSprite icon = new WSprite(iconTexture);
        panel.add(icon,x,y,9,9);
        WBar newBar = new WBar(barBg,progressTexture,value,max, WBar.Direction.RIGHT);
        newBar.setProperties(petEntity.propertyDelegate);
        panel.add(newBar,x+12,y,100,9);
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
        //Don't show equipment slots
        for(int i = petEntity.getEquipmentSize(); i < inventory.size(); i++){
            WItemSlot itemSlot = WItemSlot.of(inventory, i);
            petInventoryPanel.add(itemSlot,x++*18,y,16,16);
            if(x != 0 && (x) % 9 == 0){
                y += 18;
                x = 0;
            }
        }
    }

    private void buildPetEquipment(WPlainPanel panel, int x, int y){
        int line = 0;
        int column = 0;
        for(int i = 0; i < petEntity.getEquipmentSize(); i ++){
               if(i==0){
                   buildEquipmentSlot(panel,swordIcon,i,x+column++*18,y+line*18);
               } else if(i==1){
                   buildEquipmentSlot(panel,armorIcon,i,x+column++*18,y+line*18);
               } else{
                   buildEquipmentSlot(panel,upgradeIcon,i,x+column++*18,y+line*18);
               }
               if(column!=0 && column%2==0){
                   line++;
                   column=0;
               }
        }
    }
    private void buildEquipmentSlot(WPlainPanel panel, Texture slotTexture,int slot, int x, int y){
        WItemSlot equipmentSlot =  WItemSlot.of(petEntity.getInventory(),slot);
        equipmentSlot.setIcon(new TextureIcon(slotTexture));
        panel.add(equipmentSlot,x,y);
    }

    /**
     * This makes the root (tab) panel transparent
     */
    @Override
    public void addPainters() {}
}