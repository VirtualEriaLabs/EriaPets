package com.virtualeria.eriapets.client.gui;

import com.virtualeria.eriapets.entities.BasePetEntity;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;

public class WEntityViewer extends WWidget {

    private final BasePetEntity petEntity;
    private final int size;

    public WEntityViewer(BasePetEntity petEntity, int size){
        this.petEntity = petEntity;
        this.size = size;
    }

    @Override
    public boolean canResize(){
        return true;
    }

    /**
     * Draw a black box like the player viewer in vanilla player inventory screen
     */
    @Environment(EnvType.CLIENT)
    @Override
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        ScreenDrawing.drawBeveledPanel(matrices, x-1, y-1, width+2, height+2, 0xB8000000, Color.BLACK.toRgb(), 0xB8FFFFFF);
        InventoryScreen.drawEntity(x + width / 2, y + height - 4, size, -mouseX, -mouseY, petEntity);
    }
}
