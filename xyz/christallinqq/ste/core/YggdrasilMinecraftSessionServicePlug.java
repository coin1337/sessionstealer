package xyz.christallinqq.ste.core;

import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;

public class YggdrasilMinecraftSessionServicePlug extends YggdrasilMinecraftSessionService
{
    public YggdrasilMinecraftSessionServicePlug(final YggdrasilAuthenticationService authenticationService) {
        super(authenticationService);
    }
}
