package net.ntrdeal.realapi.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.ntrdeal.realapi.tag.RealDamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class RealDamageTypeTagProvider extends FabricTagsProvider<DamageType> {
    public RealDamageTypeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, Registries.DAMAGE_TYPE, lookup);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.tag(RealDamageTypeTags.RANGED_ATTACK_MULTIPLIED).forceAddTag(DamageTypeTags.IS_PROJECTILE);
    }
}
