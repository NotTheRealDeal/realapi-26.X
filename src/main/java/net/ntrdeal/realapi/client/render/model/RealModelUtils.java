package net.ntrdeal.realapi.client.render.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.ntrdeal.realapi.client.render.model.condition.All;
import net.ntrdeal.realapi.client.render.model.condition.Any;
import net.ntrdeal.realapi.client.render.model.condition.FirstPerson;
import net.ntrdeal.realapi.client.render.model.condition.LeftHand;

import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public final class RealModelUtils {
    private RealModelUtils(){}

    public static FirstPerson firstPerson() {return FirstPerson.INSTANCE;}
    public static LeftHand leftHand() {return LeftHand.INSTANCE;}

    public static All all(ConditionalItemModelProperty... properties) {
        return all(Arrays.asList(properties));
    }

    public static All all(List<ConditionalItemModelProperty> properties) {
        return new All(properties);
    }

    public static Any any(ConditionalItemModelProperty... properties) {
        return any(Arrays.asList(properties));
    }

    public static Any any(List<ConditionalItemModelProperty> properties) {
        return new Any(properties);
    }
}