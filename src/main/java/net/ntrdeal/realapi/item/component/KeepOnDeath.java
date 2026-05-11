package net.ntrdeal.realapi.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public class KeepOnDeath implements TooltipProvider {
    public static final KeepOnDeath INSTANCE = new KeepOnDeath();
    public static final Codec<KeepOnDeath> CODEC = MapCodec.unitCodec(INSTANCE);
    public static final StreamCodec<FriendlyByteBuf, KeepOnDeath> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final String TEXT_STRING = "item.realapi.keep_on_death";
    public static final Component TEXT = Component.translatable(TEXT_STRING).withStyle(ChatFormatting.BLUE);

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter getter) {
        consumer.accept(TEXT);
    }
}