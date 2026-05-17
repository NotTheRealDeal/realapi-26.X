package net.ntrdeal.realapi.mixin.client.event;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.ItemSlotMouseAction;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.ntrdeal.realapi.client.event.ContainerScreenEvents;
import net.ntrdeal.realapi.data.mixin.RealMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin<T extends AbstractContainerMenu> extends Screen implements MenuAccess<T>, RealMixin<AbstractContainerScreen<T>> {
    protected AbstractContainerScreenMixin(Component title) {
        super(title);
    }

    @WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;addItemSlotMouseAction(Lnet/minecraft/client/gui/ItemSlotMouseAction;)V"))
    private void ntrdeal$containerScreenInitEvents(AbstractContainerScreen<T> screen, ItemSlotMouseAction itemSlotMouseAction, Operation<Void> original) {
        original.call(screen, itemSlotMouseAction);
        ContainerScreenEvents.SLOT_MOUSE_ACTION.invoker().add(this.minecraft, action -> original.call(screen, action));
        ContainerScreenEvents.INIT.invoker().init(screen, this.minecraft);
    }
}
