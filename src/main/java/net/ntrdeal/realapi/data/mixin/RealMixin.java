package net.ntrdeal.realapi.data.mixin;

public interface RealMixin<O> {
    @SuppressWarnings("unchecked")
    default O getThis() {
        return (O)this;
    }
}