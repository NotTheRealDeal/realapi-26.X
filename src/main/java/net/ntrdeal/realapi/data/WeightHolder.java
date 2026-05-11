package net.ntrdeal.realapi.data;

import com.mojang.serialization.DataResult;
import net.minecraft.world.item.ItemInstance;
import org.apache.commons.lang3.math.Fraction;
import org.jspecify.annotations.Nullable;

public interface WeightHolder {
    @Nullable DataResult<Fraction> getWeight(ItemInstance instance, Fraction multiplier);
}
