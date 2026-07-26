package net.ntrdeal.realapi;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.ntrdeal.realapi.datagen.client.language.ModEnglishProvider;
import net.ntrdeal.realapi.datagen.tag.RealDamageTypeTagProvider;

public class RealAPIDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(RealDamageTypeTagProvider::new);

        pack.addProvider(ModEnglishProvider::new);
    }
}