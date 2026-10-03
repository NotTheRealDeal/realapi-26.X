package net.ntrdeal.realapi.util.codec;

import com.mojang.datafixers.util.Pair;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.HashSet;
import java.util.Set;

public final class StreamCodecUtil {
    private StreamCodecUtil(){}

    public static <B extends ByteBuf, S> StreamCodec< B, Set<S>> set(StreamCodec<B, S> codec) {
        return ByteBufCodecs.collection(HashSet::new, codec);
    }

    public static <B, F, S> StreamCodec<B, Pair<F, S>> pair(
            StreamCodec<? super B, F> first,
            StreamCodec<? super B, S> second
    ) {
        return StreamCodec.composite(
                first, Pair::getFirst,
                second, Pair::getSecond,
                Pair::of
        );
    }
}