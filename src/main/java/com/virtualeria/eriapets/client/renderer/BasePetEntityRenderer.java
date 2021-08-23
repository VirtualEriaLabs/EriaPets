package com.virtualeria.eriapets.client.renderer;

import com.virtualeria.eriapets.client.model.BasePetEntityModel;
import com.virtualeria.eriapets.entities.BasePetEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.MinecraftClientGame;
import net.minecraft.client.font.TextRenderer;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.VertexConsumerProvider;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;

import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.awt.*;


@Environment(EnvType.CLIENT)
public class BasePetEntityRenderer extends GeoEntityRenderer<BasePetEntity> {


    public BasePetEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BasePetEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

    @Override
    public void render(BasePetEntity entity, float entityYaw, float partialTicks, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, matrices, vertexConsumers, packedLightIn);
    }

    public void drawHungry(MatrixStack matrices,BasePetEntity entity,VertexConsumerProvider vertexConsumers,int packedLightIn){
        Text text = new TranslatableText("Hambre : " + entity.getHungry());
        @SuppressWarnings("rawtypes") EntityRenderer entityRenderer = (EntityRenderer) (Object) this;

        float height = entity.getHeight() + 0.8F;
        int y = 10;
        matrices.push();
        matrices.translate(0.0D, height, 0.0D);
        matrices.multiply(this.dispatcher.getRotation());

        matrices.scale(-0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = matrices.peek().getModel();
        TextRenderer textRenderer = entityRenderer.getTextRenderer();
        float x = (float) (-textRenderer.getWidth(text) / 2);

        float backgroundOpacity = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
        int backgroundColor = (int) (backgroundOpacity * 255.0F) << 24;

        textRenderer.draw(text, x, (float) y, Color.CYAN.getRGB(), false, matrix4f, vertexConsumers, false, backgroundColor, packedLightIn);
        matrices.multiply(new Quaternion(0,1,0,0));
        matrices.pop();
    }
}
