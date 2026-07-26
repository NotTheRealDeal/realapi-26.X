package net.ntrdeal.realapi.client.render.model.condition;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record FirstPerson() implements ConditionalItemModelProperty {
    public static final FirstPerson INSTANCE = new FirstPerson();
    public static final MapCodec<FirstPerson> MAP_CODEC = MapCodec.unit(INSTANCE);

    @Override
    public boolean get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext displayContext) {
        return displayContext.firstPerson();
    }

    @Override
    public MapCodec<FirstPerson> type() {
        return MAP_CODEC;
    }
}
