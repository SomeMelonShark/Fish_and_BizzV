package net.redmelon.fishandshiz.entity.client.renderer;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.redmelon.fishandshiz.entity.client.model.BasicEntityModel;
import net.redmelon.fishandshiz.entity.custom.AngelfishEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class AngelfishRenderer extends GeoEntityRenderer<AngelfishEntity> {
    public AngelfishRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BasicEntityModel<>("angelfish", false));
        this.addLayer(new AngelfishLayerRenderer(this));
        this.shadowRadius = 0.4f;
    }

    static class AngelfishLayerRenderer extends GeoLayerRenderer<AngelfishEntity> {

        public AngelfishLayerRenderer(IGeoRenderer<AngelfishEntity> entityRendererIn) {
            super(entityRendererIn);
        }

        public void render(int color, Identifier texture, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, AngelfishEntity entity, float partialTicks) {

            if(texture != null) {
                Identifier model = this.getEntityModel().getModelResource(entity);
                float r = ((color >> 16) & 0xff) / 255f;
                float g = ((color >> 8) & 0xff) / 255f;
                float b = (color & 0xff) / 255f;
                int overlay = OverlayTexture.getUv(0,
                        entity.hurtTime > 0 || entity.deathTime > 0);

                this.getRenderer().render(this.getEntityModel().getModel(model), entity, partialTicks, this.getRenderType(texture), matrixStackIn, bufferIn,
                        bufferIn.getBuffer(this.getRenderType(texture)), packedLightIn, overlay, r, g, b, 1f);
            }
        }

        @Override
        public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, AngelfishEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

            this.render(entity.getBaseColor().color(), this.getEntityTexture(entity), matrixStackIn, bufferIn, packedLightIn, entity, partialTicks);
            this.render(entity.getPatternColor().color(), entity.getPattern().texture(), matrixStackIn, bufferIn, packedLightIn, entity, partialTicks);
        }

        @Override
        public RenderLayer getRenderType(Identifier texture) {
            return RenderLayer.getEntityCutoutNoCull(texture);
        }
    }
}
