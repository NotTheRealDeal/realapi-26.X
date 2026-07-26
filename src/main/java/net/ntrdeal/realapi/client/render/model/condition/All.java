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
public record All(List<ConditionalItemModelProperty> properties) implements ConditionalItemModelProperty {
    public static final MapCodec<All> MAP_CODEC = ConditionalItemModelProperties.MAP_CODEC.codec().listOf().xmap(
            All::new, All::properties
    ).fieldOf("properties");

    @Override
    public boolean get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext displayContext) {
        for (ConditionalItemModelProperty property : this.properties) {
            if (!property.get(itemStack, level, owner, seed, displayContext)) return false;
        }

        return true;
    }

    @Override
    public MapCodec<All> type() {
        return MAP_CODEC;
    }
}
