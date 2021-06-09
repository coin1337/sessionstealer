package xyz.christallinqq.ste.core.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import xyz.christallinqq.ste.core.SessionTokenExploit;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Minecraft.class })
public class MinecraftMixin
{
    @Inject(method = { "<init>" }, at = { @At("RETURN") })
    private void activateFakeSession(final GameConfiguration gameConfig, final CallbackInfo ci) {
        SessionTokenExploit.setNewSession();
    }
}
