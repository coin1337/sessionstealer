package xyz.christallinqq.ste.core.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import xyz.christallinqq.ste.core.EpicAuthenticationService;
import xyz.christallinqq.ste.core.YggdrasilMinecraftSessionServicePlug;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ YggdrasilAuthenticationService.class })
public class YggdrasilAuthenticationServiceMixin
{
    @Inject(method = { "createMinecraftSessionService" }, cancellable = true, remap = false, at = { @At("HEAD") })
    public void createSpoofedSessionService(final CallbackInfoReturnable<MinecraftSessionService> cir) {
        cir.setReturnValue((MinecraftSessionService)new EpicAuthenticationService((MinecraftSessionService)new YggdrasilMinecraftSessionServicePlug((YggdrasilAuthenticationService)this)));
        cir.cancel();
    }
}
