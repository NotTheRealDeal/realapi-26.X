package net.ntrdeal.realapi.item.stack_holder;

import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.ntrdeal.realapi.data.DataKey;
import net.ntrdeal.realapi.data.data_mapper.DataMapper;
import org.apache.commons.lang3.math.Fraction;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class HolderBuilder<T extends StackHolder<T>> implements StackHolder<T>, DataMapper {
    private final Map<DataKey<?>, Object> dataMap = new Reference2ObjectOpenHashMap<>();
    public final T holder;
    public final List<ItemStack> stacks;
    public Fraction weight;
    private int index;

    public HolderBuilder(T holder) {
        this.holder = holder;
        DataResult<Fraction> weight = holder.weight();

        if (weight.isError()) {
            this.stacks = new ArrayList<>();
            this.weight = Fraction.ZERO;
            this.index = -1;
        } else {
            this.stacks = new ArrayList<>(holder.size());

            for (ItemStackTemplate template : holder.stacks()) {
                this.stacks.add(template.create());
            }

            this.weight = weight.getOrThrow();
            this.index = holder.index();
        }
    }

    @Override public List<ItemStackTemplate> stacks() {return Collections.emptyList();}
    @Override public Supplier<DataResult<Fraction>> weightSupplier() {return () -> DataResult.error(() -> "This is in the builder.");}
    @Override public Fraction scale() {return this.holder.scale();}
    @Override public int maxStackSize(ItemInstance instance) {return this.holder.maxStackSize(instance);}
    @Override public boolean canInsert(ItemStack stack) {return this.holder.canInsert(stack);}
    @Override public Stream<ItemStack> stackStream() {return this.stacks.stream();}
    @Override public int size() {return this.stacks.size();}
    @Override public boolean isEmpty() {return this.stacks.isEmpty();}
    @Override public int index() {return this.index;}
    @Override public @Nullable ItemStackTemplate getSelectedItem() {return null;}
    @Override public int getNumberOfItemsToShow() {return 0;}
    @Override public DataResult<Fraction> weight() {return DataResult.success(this.weight);}
    @Override public HolderBuilder<T> builder() {return this;}
    @Override public @Nullable DataResult<Fraction> overrideWeight(ItemInstance instance) {return this.holder.overrideWeight(instance);}
    @Override public DataResult<Fraction> getPerWeight(ItemInstance instance) {return this.holder.getPerWeight(instance);}
    @Override public DataResult<Fraction> getWeight(ItemInstance instance) {return this.holder.getWeight(instance);}
    @Override public Map<DataKey<?>, Object> dataMap() {return this.dataMap;}

    public int getMaxAmount(Fraction weight) {
        return Math.max(Fraction.ONE.subtract(this.weight).divideBy(weight).intValue(), 0);
    }

    public void addWeight(Fraction weight, int count) {
        this.weight = this.weight.add(weight.multiplyBy(Fraction.getFraction(count, 1)));
    }

    public void subtractWeight(Fraction weight, int count) {
        this.addWeight(weight, -count);
    }

    public ItemStack tryAdd(ItemStack addingStack) {
        if (!this.canInsert(addingStack)) return ItemStack.EMPTY;
        DataResult<Fraction> result = this.getPerWeight(addingStack);
        if (result.isError()) return ItemStack.EMPTY;
        Fraction itemWeight = result.getOrThrow();
        int adding = Math.min(this.getMaxAmount(itemWeight), addingStack.count());
        if (adding == 0) return ItemStack.EMPTY;
        this.subtractWeight(itemWeight, adding);
        ItemStack returningStack = addingStack.copyWithCount(adding);
        ItemStack insertingStack = addingStack.split(adding);

        for (ItemStack listedStack : this.stacks.reversed()) {
            if (insertingStack.isEmpty() || !insertingStack.isStackable()) break;
            if (listedStack.isEmpty() || !listedStack.isStackable() || !ItemStack.isSameItemSameComponents(insertingStack, listedStack)) continue;
            int shrink = Math.min(insertingStack.count(), this.maxStackSize(listedStack) - listedStack.count());
            if (shrink <= 0) continue;
            listedStack.grow(shrink);
            insertingStack.shrink(shrink);
        }

        while (!insertingStack.isEmpty()) {
            int split = Math.min(insertingStack.count(), this.maxStackSize(insertingStack));
            this.stacks.addFirst(insertingStack.split(split));
        }

        return returningStack;
    }

    public ItemStack trySlotAdd(Player player, Slot slot) {
        ItemStack slottedStack = slot.getItem();
        if (!this.canInsert(slottedStack)) return ItemStack.EMPTY;
        DataResult<Fraction> itemWeight = this.getPerWeight(slottedStack);
        if (itemWeight.isError()) return ItemStack.EMPTY;
        int adding = this.getMaxAmount(itemWeight.getOrThrow());
        return this.tryAdd(slot.safeTake(slottedStack.count(), adding, player));
    }

    public ItemStack removeStack(int index, int count) {
        if (count == 0 || this.stacks.isEmpty() || !this.isIndexAllowed(index)) return ItemStack.EMPTY;
        ItemStack removingStack = this.stacks.get(index);
        int removing = count == -1 ? removingStack.count() : Math.min(removingStack.count(), count);
        if (removing == 0) return ItemStack.EMPTY;
        DataResult<Fraction> itemWeight = this.getPerWeight(removingStack);
        if (itemWeight.isError()) return ItemStack.EMPTY;
        ItemStack removedStack = removingStack.split(removing);
        if (removedStack.isEmpty()) this.stacks.remove(index);
        this.subtractWeight(itemWeight.getOrThrow(), removing);
        return removedStack;
    }

    public int insertStack(ItemStack stack, int index, int count) {
        if (count == 0 || !this.isIndexAllowed(index)) return 0;
        DataResult<Fraction> result = this.getPerWeight(stack);
        if (result.isError()) return 0;
        Fraction itemWeight = result.getOrThrow();
        int adding = Math.min(Math.min(this.maxStackSize(stack), count == -1 ? stack.count() : Math.min(stack.count(), count)), this.getMaxAmount(itemWeight));
        if (adding == 0) return 0;
        this.stacks.add(index, stack.split(adding));
        this.addWeight(itemWeight, adding);
        return adding;
    }

    public ItemStack removeStack(int index) {return this.removeStack(index, -1);}

    public ItemStack removeSelectedStack(int count) {
        ItemStack removed = this.removeStack(this.isIndexAllowed(this.index()) ? this.index() : 0, count);
        if (!removed.isEmpty()) this.setIndex(-1);
        return removed;
    }

    public ItemStack removeSelectedStack() {return this.removeSelectedStack(-1);}

    public List<ItemStack> addAll(List<ItemStack> stacks, Consumer<ItemStack> consumer) {
        stacks.forEach(stack -> {
            ItemStack added = this.tryAdd(stack);
            if (!added.isEmpty()) consumer.accept(added);
        });
        return stacks.stream().filter(stack -> !stack.isEmpty()).toList();
    }

    public boolean isIndexAllowed(int index) {
        return index == 0 || (index > 0 && index < this.size());
    }

    public void setIndex(int index) {
        this.index = this.index != index && this.isIndexAllowed(index) ? index : -1;
    }

    public List<ItemStackTemplate> templates() {
        return this.stackStream().filter(stack -> !stack.isEmpty()).map(ItemStackTemplate::fromNonEmptyStack).toList();
    }

    public T build() {
        return this.build(this);
    }

    @Override
    public T build(HolderBuilder<T> builder) {
        return this.holder.build(builder);
    }
}