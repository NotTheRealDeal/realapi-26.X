package net.ntrdeal.realapi.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.properties.conditional.ItemModelPropertyTest;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record Update<T>(
        ItemModelResolver resolver,
        ItemStackRenderState state,
        ItemStack stack,
        ItemDisplayContext displayContext,
        @Nullable T data,
        @Nullable ClientLevel level,
        @Nullable ItemOwner owner,
        int seed
){
    public void updateModel(ItemModel model) {
        model.update(this.state, this.stack, this.resolver, this.displayContext, this.level, this.owner, this.seed);
    }

    public boolean test(ItemModelPropertyTest test) {
        return test.get(this.stack, this.level, this.getOwnerEntity(), this.seed, this.displayContext);
    }

    public float tickDelta() {
        Entity entity = this.getOwnerEntity();
        if (this.level == null || entity == null) return 0;
        return Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(this.level.tickRateManager().isEntityFrozen(entity));
    }

    @Nullable
    public LivingEntity getOwnerEntity() {
        return this.owner == null ? null : this.owner.asLivingEntity();
    }
}