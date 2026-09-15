package net.ntrdeal.realapi.mixin.attribute;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(AttributeInstance.class)
public interface PermanentModifiers {
    @Accessor(value = "permanentModifiers")
    Map<Identifier, AttributeModifier> get();
}
