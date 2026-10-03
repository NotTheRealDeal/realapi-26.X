package net.ntrdeal.realapi.util.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.IdMap;
import net.minecraft.core.IdMapper;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.function.Function;

public class CodecMapper<K, V> {
    private final ExtraCodecs.LateBoundIdMapper<K, MapCodec<? extends V>> codecMap = new ExtraCodecs.LateBoundIdMapper<>();
    private final IdMapper<StreamCodec<? extends ByteBuf, ? extends V>> streamMap = new IdMapper<>();

    public void register(K key, MapCodec<? extends V> codec, StreamCodec<? extends ByteBuf, ? extends V> streamCodec) {
        this.codecMap.put(key, codec);
        this.streamMap.add(streamCodec);
    }

    public Codec<V> codec(Codec<K> keyCodec, Function<V, MapCodec<? extends V>> toCodec) {
        return this.codecMap.codec(keyCodec).dispatch(toCodec, Function.identity());
    }

    @SuppressWarnings("unchecked")
    public StreamCodec<ByteBuf, V> streamCodec(Function<V, StreamCodec<? extends ByteBuf, ? extends V>> toStreamCodec) {
        return ByteBufCodecs.idMapper((IdMap<StreamCodec<ByteBuf, V>>)(IdMap<?>) this.streamMap).dispatch(
                (Function<V, StreamCodec<ByteBuf, V>>)(Function<?, ?>) toStreamCodec, Function.identity()
        );
    }
}
