package net.ntrdeal.realapi.util.codec;

import com.mojang.serialization.Codec;

import java.util.List;
import java.util.Set;

public final class CodecUtil {
    private CodecUtil(){}

    public static <C> Codec<Set<C>> set(Codec<C> codec) {
        return codec.listOf().xmap(Set::copyOf, List::copyOf);
    }
}