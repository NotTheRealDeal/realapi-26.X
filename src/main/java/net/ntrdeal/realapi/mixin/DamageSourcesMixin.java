package net.ntrdeal.realapi.mixin;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.damagesource.DamageSources;
import net.ntrdeal.realapi.data.data_mapper.DataKey;
import net.ntrdeal.realapi.data.data_mapper.DataKeyMap;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import net.ntrdeal.realapi.entity.DamageSourcesKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(DamageSources.class)
public abstract class DamageSourcesMixin implements RealMixin<DamageSources>, DataKeyMap {
    @Unique private final Map<DataKey<?>, Object> dataMap = new Reference2ObjectOpenHashMap<>();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void ntrdeal$addSources(RegistryAccess registries, CallbackInfo ci) {
        DamageSources sources = this.getThis();
        DamageSourcesKey.KEYS.forEach(key -> this.putData(key, key.create(registries, sources)));
    }

    @Override public Map<DataKey<?>, Object> dataMap() {return this.dataMap;}
}
