package net.redmelon.fishandshiz;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.redmelon.fishandshiz.data.ModModelProvider;

public class FishAndBizzVDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		fabricDataGenerator.addProvider(ModModelProvider::new);
	}
}
