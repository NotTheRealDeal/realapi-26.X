package net.ntrdeal.realapi.mixin.component;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.component.TypedDataComponent;
import net.ntrdeal.realapi.item.component.type.RealComponentMap;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mixin(PatchedDataComponentMap.class)
public abstract class PatchedDataComponentMapMixin implements RealComponentMap {
    @Unique private Reference2ObjectMap<Class<?>, Set<DataComponentType<?>>> classToType = new Reference2ObjectOpenHashMap<>();
    @Unique private Reference2ObjectMap<DataComponentType<?>, Set<Class<?>>> typeToClass = new Reference2ObjectOpenHashMap<>();

    @WrapMethod(method = "set(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;")
    private <T> T ntrdeal$updateCache(DataComponentType<T> type, T value, Operation<T> original) {
        boolean hadBefore = this.has(type);
        T returning = original.call(type, value);
        boolean hasNow = this.has(type);
        if (hasNow == hadBefore) return returning;
        if (hasNow) this.addToCache(this.getTyped(type));
        else this.removeFromCache(type);
        return returning;
    }

    @WrapMethod(method = "remove")
    private <T> @Nullable T ntrdeal$updateCache(DataComponentType<? extends T> type, Operation<T> original) {
        boolean hadBefore = this.has(type);
        T returning = original.call(type);
        boolean hasNow = this.has(type);
        if (hasNow == hadBefore) return returning;
        if (hasNow) this.addToCache(this.getTyped(type));
        else this.removeFromCache(type);
        return returning;
    }

    @WrapMethod(method = "applyPatch(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)V")
    private void ntrdeal$applyPatchUpdateCache(DataComponentType<?> type, Object value, Operation<Void> original) {
        boolean hadBefore = this.has(type);
        original.call(type, value);
        boolean hasNow = this.has(type);
        if (hasNow == hadBefore) return;
        if (hasNow) this.addToCache(this.getTyped(type));
        else this.removeFromCache(type);
    }

    @Inject(method = {"restorePatch", "clearPatch"}, at = @At("RETURN"))
    private void ntrdeal$clearCache(CallbackInfo ci) {
        this.clearCache();
    }

    @WrapMethod(method = "copy")
    private PatchedDataComponentMap ntrdeal$copyCache(Operation<PatchedDataComponentMap> original) {
        PatchedDataComponentMap map = original.call();
        map.setCache(this.classToType, this.typeToClass);
        return map;
    }

    @Override @SuppressWarnings("unchecked")
    public <T> Set<DataComponentType<? extends T>> requestTypes(Class<? extends T> clazz) {
        return (Set<DataComponentType<? extends T>>)(Set<?>) this.classToType.computeIfAbsent(clazz, _ -> {
            Set<DataComponentType<?>> types = new HashSet<>();

            for (TypedDataComponent<?> component : this) {
                if (!clazz.isInstance(component.value())) continue;
                DataComponentType<?> type = component.type();
                types.add(type);
                this.typeToClass.computeIfAbsent(type, _ -> new HashSet<>()).add(clazz);
            }

            return types;
        });
    }

    @Override
    public void setCache(
            Reference2ObjectMap<Class<?>, Set<DataComponentType<?>>> classToType,
            Reference2ObjectMap<DataComponentType<?>, Set<Class<?>>> typeToClass
    ) {
        this.classToType = new Reference2ObjectOpenHashMap<>(classToType);
        this.typeToClass = new Reference2ObjectOpenHashMap<>(typeToClass);
    }

    @Unique
    private void addToCache(@Nullable TypedDataComponent<?> component) {
        if (component == null) return;

        DataComponentType<?> type = component.type();
        Object value = component.value();

        for (Map.Entry<Class<?>, Set<DataComponentType<?>>> entry : Reference2ObjectMaps.fastIterable(this.classToType)) {
            if (!entry.getKey().isInstance(value)) continue;
            entry.getValue().add(type);
            this.typeToClass.computeIfAbsent(type, _ -> new HashSet<>()).add(entry.getKey());
        }
    }

    @Unique
    private void removeFromCache(DataComponentType<?> type) {
        Set<Class<?>> collected = this.typeToClass.remove(type);
        if (collected != null) collected.forEach(clazz -> this.classToType.get(clazz).remove(type));
    }

    @Unique
    private void clearCache() {
        this.classToType.clear();
        this.typeToClass.clear();
    }
}
