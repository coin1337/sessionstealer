package xyz.christallinqq.ste.core;

import org.apache.logging.log4j.LogManager;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.Map;
import java.net.InetAddress;
import org.apache.http.client.methods.CloseableHttpResponse;
import java.io.IOException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.AuthenticationException;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.HttpGet;
import com.mojang.authlib.GameProfile;
import org.apache.logging.log4j.Logger;
import com.mojang.authlib.minecraft.MinecraftSessionService;

public class EpicAuthenticationService implements MinecraftSessionService
{
    private static final Logger log;
    private final MinecraftSessionService wrapped;
    
    public EpicAuthenticationService(final MinecraftSessionService wrapped) {
        this.wrapped = wrapped;
    }
    
    public void joinServer(final GameProfile profile, final String authenticationToken, final String serverId) throws AuthenticationException {
        final String authUrl = String.format("http://session.minecraft.net/game/joinserver.jsp?user=%s&sessionId=%s&serverId=%s", profile.getName(), authenticationToken, serverId);
        EpicAuthenticationService.log.info(String.format("trying %s", authUrl));
        try (final CloseableHttpResponse response = HttpClient.getInstance().getClosableHttpClient().execute((HttpUriRequest)new HttpGet(authUrl))) {
            final String responseStr = EntityUtils.toString(response.getEntity());
            if (!responseStr.equalsIgnoreCase("OK")) {
                throw new AuthenticationException(String.format("Failed to authenticate: %s", responseStr));
            }
        }
        catch (IOException e) {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", (Throwable)e);
        }
    }
    
    public GameProfile hasJoinedServer(final GameProfile user, final String serverId, final InetAddress address) throws AuthenticationUnavailableException {
        return this.wrapped.hasJoinedServer(user, serverId, address);
    }
    
    public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(final GameProfile profile, final boolean requireSecure) {
        return (Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>)this.wrapped.getTextures(profile, requireSecure);
    }
    
    public GameProfile fillProfileProperties(final GameProfile profile, final boolean requireSecure) {
        return this.wrapped.fillProfileProperties(profile, requireSecure);
    }
    
    static {
        log = LogManager.getLogger((Class)EpicAuthenticationService.class);
    }
}
