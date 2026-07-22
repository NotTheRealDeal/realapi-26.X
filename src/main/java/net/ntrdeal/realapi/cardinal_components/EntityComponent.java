package net.ntrdeal.realapi.cardinal_components;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.ladysnake.cca.api.v3.component.ComponentProvider;

public interface EntityComponent<C extends EntityComponent<C>> extends KeyedComponent<C> {
    Entity entity();

    @Override default ComponentProvider access() {return (ComponentProvider) this.entity();}

    default Level level() {
        return this.entity().level();
    }

    default boolean isClient() {
        return this.level().isClientSide();
    }

    default Vec3 pos() {
        return this.entity().position();
    }

    default double getX() {return this.entity().getX();}
    default double getY() {return this.entity().getY();}
    default double getZ() {return this.entity().getZ();}

    default Vec3 movement() {
        return this.entity().getDeltaMovement();
    }
}
