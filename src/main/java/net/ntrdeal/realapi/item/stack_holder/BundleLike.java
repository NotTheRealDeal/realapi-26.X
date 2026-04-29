package net.ntrdeal.realapi.item.stack_holder;

import com.google.common.base.Suppliers;
import com.mojang.serialization.DataResult;
import net.minecraft.world.item.ItemStackTemplate;
import org.apache.commons.lang3.math.Fraction;

import java.util.List;
import java.util.function.Supplier;

public abstract class BundleLike<T extends StackHolder<T>> implements StackHolder<T> {
    public final Supplier<DataResult<Fraction>> weight = Suppliers.memoize(this::computeMyWeight);
    public final List<ItemStackTemplate> stacks;
    public final Fraction scale;
    public final int index;

    public BundleLike(List<ItemStackTemplate> stacks, Fraction scale, int index) {
        this.stacks = stacks;
        this.scale = scale;
        this.index = index;
    }

    public BundleLike(List<ItemStackTemplate> stacks, double scale, int index) {
        this(stacks, Fraction.getFraction(scale).invert(), index);
    }

    @Override
    public List<ItemStackTemplate> stacks() {
        return this.stacks;
    }

    @Override
    public Fraction scale() {
        return this.scale;
    }

    @Override
    public Supplier<DataResult<Fraction>> weightSupplier() {
        return this.weight;
    }

    @Override
    public int index() {
        return this.index;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StackHolder<?> holder) {
            return this.isEqualToHolder(holder);
        } else return false;
    }

    @Override
    public int hashCode() {
        return this.getHash();
    }

    @Override
    public String toString() {
        return this.getString();
    }
}
