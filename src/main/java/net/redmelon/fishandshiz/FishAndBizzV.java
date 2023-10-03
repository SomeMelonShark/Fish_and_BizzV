package net.redmelon.fishandshiz;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.redmelon.fishandshiz.entity.ModEntities;
import net.redmelon.fishandshiz.entity.custom.AngelfishEntity;
import net.redmelon.fishandshiz.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class FishAndBizzV implements ModInitializer {
	public static final String MOD_ID = "fishandshiz";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		//ModItems.registerModitems();

		GeckoLib.initialize();

		FabricDefaultAttributeRegistry.register(ModEntities.ANGELFISH, AngelfishEntity.setAttributes());
	}
}