package net.ntrdeal.realapi.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.runtime.IJeiRuntime;

public interface IRealModPlugin extends IModPlugin {
    default void postRuntime(IJeiRuntime runtime) {}
}
