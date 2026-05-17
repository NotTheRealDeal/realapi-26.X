package net.ntrdeal.realapi.entity;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.ntrdeal.realapi.entity.event.EntityAttributeEvents;
import net.ntrdeal.realapi.reference.RealAttributeIds;
import net.ntrdeal.realapi.tag.RealAttributeTags;

public class RealAttributes {
    public static final Holder<Attribute> MOVEMENT_SCALE = AttributeBuilder.of(RealAttributeIds.MOVEMENT_SCALE)
            .range(1d, 0d, 1024d).sync().sentiment(Attribute.Sentiment.POSITIVE).buildAndRegister();

    public static final Holder<Attribute> SHIELD_FRAGILITY = AttributeBuilder.of(RealAttributeIds.SHIELD_FRAGILITY)
            .range(1d, 0d, 1024d).sync().sentiment(Attribute.Sentiment.NEGATIVE).buildAndRegister();

    public static final Holder<Attribute> ARMOR_PENETRATION = AttributeBuilder.of(RealAttributeIds.ARMOR_PENETRATION)
            .range(0d, 0d, 1024d).sync().sentiment(Attribute.Sentiment.POSITIVE).buildAndRegister();

    public static final Holder<Attribute> APPETITE = AttributeBuilder.of(RealAttributeIds.APPETITE)
            .range(1d, 0.25d, 1024d).sync().sentiment(Attribute.Sentiment.NEGATIVE).buildAndRegister();

    public static final Holder<Attribute> CHARGE_TIME = AttributeBuilder.of(RealAttributeIds.CHARGE_TIME)
            .range(1d, 0d, 1024d).sync().sentiment(Attribute.Sentiment.NEGATIVE).buildAndRegister();

    public static final Holder<Attribute> RANGED_ATTACK_MULTIPLIER = AttributeBuilder.of(RealAttributeIds.RANGED_ATTACK_MULTIPLIER)
            .range(1d, 0d, 1024d).sync().sentiment(Attribute.Sentiment.POSITIVE).buildAndRegister();

    public static final Holder<Attribute> BANE_OF_ADOLESCENCE = AttributeBuilder.of(RealAttributeIds.BANE_OF_ADOLESCENCE)
            .range(0d, 0d, 1024d).sync().sentiment(Attribute.Sentiment.POSITIVE).buildAndRegister();

    public static final Holder<Attribute> FIRE_DAMAGE_MULTIPLIER = AttributeBuilder.of(RealAttributeIds.FIRE_DAMAGE_MULTIPLIER)
            .range(1d, 0d, 1024d).sync().sentiment(Attribute.Sentiment.NEGATIVE).buildAndRegister();

    public static final Holder<Attribute> DODGE_CHANCE = AttributeBuilder.of(RealAttributeIds.DODGE_CHANCE)
            .range(0d, 0d, 1d).sync().sentiment(Attribute.Sentiment.POSITIVE).buildAndRegister();

    public static final Holder<Attribute> INTELLIGENCE = AttributeBuilder.of(RealAttributeIds.INTELLIGENCE)
            .range(1d, 0d, 1024d).sync().sentiment(Attribute.Sentiment.POSITIVE).buildAndRegister();

    public static void register() {
        EntityAttributeEvents.UPDATED.register((entity, attribute) -> {
            if (attribute.is(RealAttributeTags.DIMENSIONS_REFRESHER)) entity.refreshDimensions();
        });
    }
}
