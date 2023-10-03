package net.redmelon.fishandshiz;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.client.renderer.AngelfishRenderer;

public class FishAndBizzVClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.ANGELFISH, AngelfishRenderer::new);
    }
}
