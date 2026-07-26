package net.ntrdeal.realapi.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

@Environment(EnvType.CLIENT) @FunctionalInterface
public interface SetAnglesEvents<S, M extends Model<S>> {
    Event<SetAnglesEvents<HumanoidRenderState, HumanoidModel<HumanoidRenderState>>> HUMANOID_ANGLES = EventFactory.createArrayBacked(SetAnglesEvents.class, events -> (state, model) -> {
        for (SetAnglesEvents<HumanoidRenderState, HumanoidModel<HumanoidRenderState>> event : events) {
            event.setAngles(state, model);
        }
    });

    void setAngles(S state, M model);
}
