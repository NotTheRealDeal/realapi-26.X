package net.ntrdeal.realapi.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.ntrdeal.realapi.data.mixin.ItemPerseverer;
import net.ntrdeal.realapi.entity.RealAttributes;
import net.ntrdeal.realapi.entity.event.KeepOnDeathEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Map;

@Mixin(Player.class)
public abstract class PlayerMixin extends Avatar implements ContainerUser, ItemPerseverer {
    @Shadow public abstract Inventory getInventory();

    @Unique private final Map<Integer, ItemStack> savedStacks = new HashMap<>();

    protected PlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @WrapMethod(method = "createAttributes")
    private static AttributeSupplier.Builder ntrdeal$addAttributes(Operation<AttributeSupplier.Builder> original) {
        return original.call()
                .add(RealAttributes.SHIELD_FRAGILITY)
                .add(RealAttributes.APPETITE)
                .add(RealAttributes.INTELLIGENCE);
    }

    @WrapOperation(method = "causeFoodExhaustion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V"))
    private void ntrdeal$appetite(FoodData data, float amount, Operation<Void> original) {
        original.call(data, amount * (float) this.getAttributeValue(RealAttributes.APPETITE));
    }

    @WrapOperation(method = "blockUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getSecondsToDisableBlocking()F"))
    private float ntrdeal$shieldFragility(LivingEntity entity, Operation<Float> original) {
        return original.call(entity) * (float) this.getAttributeValue(RealAttributes.SHIELD_FRAGILITY);
    }

    @Override
    protected void dropAllDeathLoot(ServerLevel level, DamageSource source) {
        this.savedStacks.clear();
        Inventory inventory = this.getInventory();

        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (KeepOnDeathEvent.EVENT.invoker().keepOnDeath((Player)(Avatar)this, stack)) this.savedStacks.put(slot, inventory.removeItem(slot, stack.count()));
        }

        super.dropAllDeathLoot(level, source);

        this.savedStacks.forEach(inventory::add);
    }

    @Override
    public Map<Integer, ItemStack> savedStacks() {
        return this.savedStacks;
    }
}