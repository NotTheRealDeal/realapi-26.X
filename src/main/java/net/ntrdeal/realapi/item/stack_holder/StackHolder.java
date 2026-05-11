package net.ntrdeal.realapi.item.stack_holder;

import com.mojang.serialization.DataResult;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.Bees;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.ntrdeal.realapi.data.WeightHolder;
import org.apache.commons.lang3.math.Fraction;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface StackHolder<T extends StackHolder<T>> extends TooltipComponent {
    List<ItemStackTemplate> stacks();
    Supplier<DataResult<Fraction>> weightSupplier();

    default Fraction scale() {
        return Fraction.ONE;
    }

    default int maxStackSize(ItemInstance instance) {
        return instance.getMaxStackSize();
    }

    default boolean canInsert(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem().canFitInsideContainerItems();
    }

    default Stream<ItemStack> stackStream() {
        return this.stacks().stream().map(ItemStackTemplate::create);
    }

    default int size() {
        return this.stacks().size();
    }

    default boolean isEmpty() {
        return this.stacks().isEmpty();
    }

    default int index() {
        return -1;
    }

    @Nullable
    default ItemStackTemplate getSelectedItem() {
        return this.index() == -1 ? null : this.stacks().get(this.index());
    }

    default int getNumberOfItemsToShow() {
        int numberOfItemStacks = this.size();
        int availableItemsToShow = numberOfItemStacks > 12 ? 11 : 12;
        int itemsOnNonFullRow = numberOfItemStacks % 4;
        int emptySpaceOnNonFullRow = itemsOnNonFullRow == 0 ? 0 : 4 - itemsOnNonFullRow;
        return Math.min(numberOfItemStacks, availableItemsToShow - emptySpaceOnNonFullRow);
    }

    default boolean isEqualToHolder(StackHolder<?> holder) {
        return this.stacks().equals(holder.stacks()) && this.scale().equals(holder.scale());
    }

    default int getHash() {
        return this.stacks().hashCode();
    }

    default String getString() {
        return "StackHolder: " + this.stacks();
    }

    default DataResult<Fraction> weight() {
        return this.weightSupplier().get();
    }

    @Nullable
    default DataResult<Fraction> overrideWeight(ItemInstance instance) {
        if (instance.typeHolder().value() instanceof WeightHolder holder) {
            return holder.getWeight(instance, this.scale());
        } else if (instance.get(DataComponents.BUNDLE_CONTENTS) instanceof BundleContents contents) {
            return contents.weight().map(weight -> weight.add(BundleLikeItem.BUNDLE_SIZE.multiplyBy(this.scale())));
        } else if (instance.get(DataComponents.BEES) instanceof Bees(List<BeehiveBlockEntity.Occupant> bees) && !bees.isEmpty()) {
            return DataResult.success(Fraction.ONE.multiplyBy(this.scale()));
        } else return null;
    }

    default DataResult<Fraction> getPerWeight(ItemInstance instance) {
        DataResult<Fraction> override = this.overrideWeight(instance);
        return override != null ? override : DataResult.success(Fraction.getFraction(1, this.maxStackSize(instance)).multiplyBy(this.scale()));
    }

    default DataResult<Fraction> getWeight(ItemInstance instance) {
        return this.getPerWeight(instance).map(weight -> weight.multiplyBy(Fraction.getFraction(instance.count(), 1)));
    }

    default DataResult<Fraction> computeMyWeight() {
        return this.computeWeight(this.stacks());
    }

    default DataResult<Fraction> computeWeight(List<? extends ItemInstance> instances) {
        Fraction totalWeight = Fraction.ZERO;
        for (ItemInstance instance : instances) {
            DataResult<Fraction> weight = this.getWeight(instance);
            if (weight.isError()) return weight;
            totalWeight = totalWeight.add(weight.getOrThrow());
        }
        return DataResult.success(totalWeight);
    }

    @SuppressWarnings("unchecked")
    default T getThis() {
        return (T) this;
    }

    T build(HolderBuilder<T> builder);

    default HolderBuilder<T> builder() {
        return new HolderBuilder<>(this.getThis());
    }
}