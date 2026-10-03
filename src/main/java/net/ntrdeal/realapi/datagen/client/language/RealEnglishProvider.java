package net.ntrdeal.realapi.datagen.client.language;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.ntrdeal.realapi.entity.RealAttributes;
import net.ntrdeal.realapi.item.component.custom.KeepOnDeath;
import net.ntrdeal.realapi.tag.*;

import java.util.concurrent.CompletableFuture;

public class RealEnglishProvider extends FabricLanguageProvider {
    public RealEnglishProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, "en_us", provider);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder builder) {
        builder.addAttribute(RealAttributes.MOVEMENT_SCALE, "Movement Scale");
        builder.addAttribute(RealAttributes.SHIELD_FRAGILITY, "Shield Fragility");
        builder.addAttribute(RealAttributes.ARMOR_PENETRATION, "Armor Penetration");
        builder.addAttribute(RealAttributes.APPETITE, "Appetite");
        builder.addAttribute(RealAttributes.CHARGE_TIME, "Charge Time");
        builder.addAttribute(RealAttributes.RANGED_ATTACK_MULTIPLIER, "Ranged Attack Multiplier");
        builder.addAttribute(RealAttributes.BANE_OF_ADOLESCENCE, "Bane of Adolescence");
        builder.addAttribute(RealAttributes.FIRE_DAMAGE_MULTIPLIER, "Fire Damage Multiplier");
        builder.addAttribute(RealAttributes.DODGE_CHANCE, "Dodge Chance");
        builder.addAttribute(RealAttributes.INTELLIGENCE, "Intelligence");
        builder.addAttribute(RealAttributes.NPC_ARMOR, "NPC Armor");

        builder.add(RealAttributeTags.DIMENSIONS_REFRESHER, "Dimensions Refresher");

        builder.add(RealDamageTypeTags.RANGED_ATTACK_MULTIPLIED, "Ranged Attack Multiplied");

        builder.add(RealItemTags.KEEP_ON_DEATH, "Keep On Death");
        builder.add(RealItemTags.HIDE_RECIPE, "Hide Recipe");

        builder.add(RealMobEffectTags.CANNOT_CLEAR, "Cannot Clear");
        builder.add(RealMobEffectTags.PLAYER_ONLY, "Player Only");

        builder.add(RealRecipeTags.REQUIRE_RECIPE, "Require Recipe");

        builder.add(KeepOnDeath.TEXT_STRING, "Kept On Death");
    }
}