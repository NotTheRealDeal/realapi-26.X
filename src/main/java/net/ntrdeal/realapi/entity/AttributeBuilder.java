package net.ntrdeal.realapi.entity;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class AttributeBuilder {
    private final ResourceKey<Attribute> key;
    private double fallback = 0d, min = 0d, max = 1024d;
    private boolean syncing = false;
    private Attribute.Sentiment sentiment = Attribute.Sentiment.POSITIVE;
    private AttributeBuilder(ResourceKey<Attribute> key){this.key = key;}

    public static AttributeBuilder of(ResourceKey<Attribute> key) {
        return new AttributeBuilder(key);
    }

    public AttributeBuilder range(double fallback, double min, double max) {
        this.fallback = fallback;
        this.min = min;
        this.max = max;
        return this;
    }

    public AttributeBuilder sync() {
        this.syncing = true;
        return this;
    }

    public AttributeBuilder sentiment(Attribute.Sentiment sentiment) {
        this.sentiment = sentiment;
        return this;
    }

    public Holder<Attribute> buildAndRegister() {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, this.key, new RangedAttribute(
                this.key.identifier().toLanguageKey("attribute"),
                this.fallback, this.min, this.max).setSyncable(this.syncing).setSentiment(this.sentiment)
        );
    }
}
