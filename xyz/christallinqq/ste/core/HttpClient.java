package xyz.christallinqq.ste.core;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;

public class HttpClient
{
    private static final HttpClient instance;
    private final CloseableHttpClient closableHttpClient;
    
    private HttpClient() {
        final RequestConfig req = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).build();
        this.closableHttpClient = HttpClientBuilder.create().setDefaultRequestConfig(req).build();
    }
    
    public CloseableHttpClient getClosableHttpClient() {
        return this.closableHttpClient;
    }
    
    public static HttpClient getInstance() {
        return HttpClient.instance;
    }
    
    static {
        instance = new HttpClient();
    }
}
