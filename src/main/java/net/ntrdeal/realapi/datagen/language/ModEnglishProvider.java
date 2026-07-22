package net.ntrdeal.realapi.datagen.language;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.ntrdeal.realapi.entity.RealAttributes;
import net.ntrdeal.realapi.item.component.KeepOnDeath;
import net.ntrdeal.realapi.tag.RealAttributeTags;
import net.ntrdeal.realapi.tag.RealItemTags;
import net.ntrdeal.realapi.tag.RealMobEffectTags;

import java.util.concurrent.CompletableFuture;

public class ModEnglishProvider extends FabricLanguageProvider {
    public ModEnglishProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder builder) {
        builder.add(RealAttributes.MOVEMENT_SCALE, "Movement Scale");
        builder.add(RealAttributes.SHIELD_FRAGILITY, "Shield Fragility");
        builder.add(RealAttributes.ARMOR_PENETRATION, "Armor Penetration");
        builder.add(RealAttributes.APPETITE, "Appetite");
        builder.add(RealAttributes.CHARGE_TIME, "Charge Time");
        builder.add(RealAttributes.RANGED_ATTACK_MULTIPLIER, "Ranged Attack Multiplier");
        builder.add(RealAttributes.BANE_OF_ADOLESCENCE, "Bane of Adolescence");
        builder.add(RealAttributes.FIRE_DAMAGE_MULTIPLIER, "Fire Damage Multiplier");
        builder.add(RealAttributes.DODGE_CHANCE, "Dodge Chance");
        builder.add(RealAttributes.INTELLIGENCE, "Intelligence");

        builder.add(RealAttributeTags.DIMENSIONS_REFRESHER, "Dimensions Refresher");
        builder.add(RealItemTags.KEEP_ON_DEATH, "Keep On Death");
        builder.add(RealMobEffectTags.CANNOT_CLEAR, "Cannot Clear");
        builder.add(RealMobEffectTags.PLAYER_ONLY, "Player Only");

        builder.add(KeepOnDeath.TEXT_STRING, "Kept On Death");
    }
}