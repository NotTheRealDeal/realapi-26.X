package net.ntrdeal.realapi.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import net.ntrdeal.realapi.entity.RealAttributes;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin  extends ProjectileWeaponItem implements RealMixin<CrossbowItem> {
    public CrossbowItemMixin(Properties properties) {
        super(properties);
    }

    @WrapMethod(method = "getChargeDuration")
    private static int ntrdeal$chargeTime(ItemStack crossbow, LivingEntity user, Operation<Integer> original) {
        return Mth.ceil(original.call(crossbow, user) * user.getAttributeValue(RealAttributes.CHARGE_TIME));
    }
}
