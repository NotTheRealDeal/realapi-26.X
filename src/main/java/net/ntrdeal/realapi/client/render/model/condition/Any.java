package net.ntrdeal.realapi.client.render.model.condition;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Environment(EnvType.CLIENT)
public record Any(List<ConditionalItemModelProperty> properties) implements ConditionalItemModelProperty {
    public static final MapCodec<Any> MAP_CODEC = ConditionalItemModelProperties.MAP_CODEC.codec().listOf().xmap(
            Any::new, Any::properties
    ).fieldOf("properties");

    @Override
    public boolean get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext displayContext) {
        for (ConditionalItemModelProperty property : this.properties) {
            if (property.get(itemStack, level, owner, seed, displayContext)) return true;
        }

        return false;
    }

    @Override
    public MapCodec<Any> type() {
        return MAP_CODEC;
    }
}
