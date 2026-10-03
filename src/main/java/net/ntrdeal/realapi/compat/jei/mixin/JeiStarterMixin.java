package net.ntrdeal.realapi.compat.jei.mixin;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import net.ntrdeal.realapi.compat.jei.IRealModPlugin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

@Mixin(targets = "mezz.jei.library.startup.JeiStarter")
public class JeiStarterMixin {
    @Shadow @Final private List<IModPlugin> plugins;

    @Inject(method = "start", at = @At(value = "INVOKE", target = "Lmezz/jei/common/Internal;setRuntime(Lmezz/jei/api/runtime/IJeiRuntime;)V", shift = At.Shift.AFTER))
    private void ntrdeal$postRuntime(CallbackInfo ci) {
        try {
            IJeiRuntime runtime = (IJeiRuntime) Class.forName("mezz.jei.common.Internal").getMethod("getJeiRuntime").invoke(null);

            Consumer<IModPlugin> consumer = plugin -> {
                if (plugin instanceof IRealModPlugin realPlugin) realPlugin.postRuntime(runtime);
            };

            Class.forName("mezz.jei.library.load.PluginCaller").getMethod(
                    "callOnPlugins", String.class, List.class, Consumer.class
            ).invoke(null, "RealAPI: PostRuntime", this.plugins, consumer);
        } catch (Exception _){}
    }
}
