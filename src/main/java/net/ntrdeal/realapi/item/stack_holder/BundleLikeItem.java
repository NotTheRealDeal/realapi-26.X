package net.ntrdeal.realapi.item.stack_holder;

import com.mojang.serialization.DataResult;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.ntrdeal.realapi.data.WeightHolder;
import org.apache.commons.lang3.math.Fraction;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BundleLikeItem<T extends StackHolder<T>> extends Item implements WeightHolder {
    public static final int FULL_BAR_COLOR = ARGB.colorFromFloat(1.0F, 1.0F, 0.33F, 0.33F);
    public static final int BAR_COLOR = ARGB.colorFromFloat(1.0F, 0.44F, 0.53F, 1.0F);
    public static final Fraction BUNDLE_SIZE = Fraction.getFraction(1, 16);

    public final DataComponentType<T> type;
    public final T empty;

    public BundleLikeItem(Properties properties, DataComponentType<T> type, T empty) {
        super(properties);
        this.type = type;
        this.empty = empty;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        T holder = stack.get(this.type);
        if (holder == null) return false;
        ItemStack slotStack = slot.getItem();
        HolderBuilder<T> builder = holder.builder();

        if (action.equals(ClickAction.PRIMARY) && !slotStack.isEmpty()) {
            if (!builder.trySlotAdd(player, slot).isEmpty()) this.playInsertSound(player);
            else this.playInsertFailSound(player);

            stack.set(this.type, builder.build());
            this.broadcastChanges(player);
            return true;
        } else if (action.equals(ClickAction.SECONDARY) && slotStack.isEmpty()) {
            ItemStack emptiedStack = builder.removeSelectedStack();

            if (!emptiedStack.isEmpty()) {
                ItemStack remainder = slot.safeInsert(emptiedStack);
                if (remainder.getCount() > 0) builder.tryAdd(remainder);
                else this.playRemoveSound(player);
            }

            stack.set(this.type, builder.build());
            this.broadcastChanges(player);
            return true;
        } else return false;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack slotStack, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (action.equals(ClickAction.PRIMARY) && slotStack.isEmpty()) {
            this.setIndex(stack, -1);
            return false;
        }

        T holder = stack.get(this.type);
        if (holder == null) return false;
        HolderBuilder<T> builder = holder.builder();

        if (action.equals(ClickAction.PRIMARY) && !slotStack.isEmpty()) {
            if (slot.allowModification(player) && !builder.tryAdd(slotStack).isEmpty()) this.playInsertSound(player);
            else this.playInsertFailSound(player);
            stack.set(this.type, builder.build());
            this.broadcastChanges(player);
            return true;
        } else if (action.equals(ClickAction.SECONDARY) && slotStack.isEmpty()) {
            if (slot.allowModification(player)) {
                ItemStack removedStack = builder.removeSelectedStack();
                if (!removedStack.isEmpty()) {
                    this.playRemoveSound(player);
                    access.set(removedStack);
                }
            }

            stack.set(this.type, builder.build());
            this.broadcastChanges(player);
            return true;
        } else return false;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        T holder = stack.get(this.type);
        if (holder == null) return false;
        return this.getWeight(holder).compareTo(Fraction.ZERO) > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        T holder = stack.get(this.type);
        if (holder == null) return 0;
        return Math.min(1 + Mth.mulAndTruncate(this.getWeight(holder), 12), 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        T holder = stack.get(this.type);
        if (holder == null) return BAR_COLOR;
        return this.getWeight(holder).compareTo(Fraction.ONE) >= 0 ? FULL_BAR_COLOR : BAR_COLOR;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int ticksRemaining) {
        if (entity instanceof Player player) {
            int useDuration = this.getUseDuration(stack, entity);
            boolean firstTick = ticksRemaining == useDuration;
            if (firstTick || ticksRemaining < useDuration - 10 && ticksRemaining % 2 == 0) {
                this.dropItem(level, player, stack);
            }
        }
        super.onUseTick(level, entity, stack, ticksRemaining);
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {
        return 200;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BUNDLE;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        TooltipDisplay display = stack.getOrDefault(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT);
        return !display.shows(this.type) ? super.getTooltipImage(stack) : Optional.ofNullable(stack.get(this.type));
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        ItemStack stack = entity.getItem();
        if (stack.get(this.type) instanceof T holder) {
            stack.set(this.type, this.empty);
            ItemUtils.onContainerDestroyed(entity, holder.stackStream());
        }
        super.onDestroyed(entity);
    }

    public Fraction getWeight(T holder) {
        return switch (holder.weight()) {
            case DataResult.Success<Fraction> success -> success.value();
            case DataResult.Error<?> _ -> Fraction.ONE;
        };
    }

    public void setIndex(ItemStack stack, int index) {
        T holder = stack.get(this.type);
        if (holder == null) return;
        HolderBuilder<T> builder = holder.builder();
        builder.setIndex(index);
        stack.set(this.type, builder.build());
    }

    public void dropItem(Level level, Player player, ItemStack stack) {
        T holder = stack.get(this.type);
        if (holder == null || holder.isEmpty()) return;
        HolderBuilder<T> builder = holder.builder();
        ItemStack droppedStack = builder.removeSelectedStack();
        if (droppedStack.isEmpty()) return;
        this.playRemoveSound(player);
        stack.set(this.type, builder.build());
        player.drop(droppedStack, true);
        this.playDropContentsSound(level, player);
        player.awardStat(Stats.ITEM_USED.get(this));
    }

    public void broadcastChanges(Player player) {
        if (player.containerMenu instanceof AbstractContainerMenu menu) {
            menu.slotsChanged(player.getInventory());
        }
    }

    public void playRemoveSound(Entity entity) {
        entity.playSound(
                SoundEvents.BUNDLE_REMOVE_ONE,
                0.8f,
                0.8f + entity.level().getRandom().nextFloat() * 0.4f
        );
    }

    public void playInsertSound(Entity entity) {
        entity.playSound(
                SoundEvents.BUNDLE_INSERT,
                0.8f,
                0.8f + entity.level().getRandom().nextFloat() * 0.4f
        );
    }

    public void playInsertFailSound(Entity entity) {
        entity.playSound(
                SoundEvents.BUNDLE_INSERT_FAIL,
                0.8f,
                0.8f + entity.level().getRandom().nextFloat() * 0.4f
        );
    }

    public void playDropContentsSound(Level level, Entity entity) {
        level.playSound(
                null,
                entity.blockPosition(),
                SoundEvents.BUNDLE_DROP_CONTENTS,
                SoundSource.PLAYERS,
                0.8f,
                0.8f + entity.level().getRandom().nextFloat() * 0.4f
        );
    }

    @Override
    public @Nullable DataResult<Fraction> getWeight(ItemInstance instance, Fraction multiplier) {
        if (instance.get(this.type) instanceof T holder) {
            return holder.weight().map(weight -> weight.add(BUNDLE_SIZE.multiplyBy(multiplier)));
        } else return null;
    }
}