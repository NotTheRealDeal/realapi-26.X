package net.ntrdeal.realapi;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import net.ntrdeal.realapi.entity.RealAttributes;
import net.ntrdeal.realapi.item.component.RealDataComponents;
import net.ntrdeal.realapi.network.RealNetworking;
import net.ntrdeal.realapi.tag.RealMobEffectTags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealAPI implements ModInitializer {
	public static final String MOD_ID = "realapi";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		RealNetworking.register();
		RealDataComponents.register();
		RealAttributes.register();

		RealMobEffectTags.register();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}