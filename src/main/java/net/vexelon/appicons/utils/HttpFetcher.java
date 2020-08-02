package net.vexelon.appicons.utils;

import net.vexelon.appicons.AbstractAppIconsBuilder;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HttpFetcher {

    private static final Logger logger = Logger.getLogger(HttpFetcher.class.getName());

    private final AbstractAppIconsBuilder.BuilderConfig config;

    public HttpFetcher(AbstractAppIconsBuilder.BuilderConfig config) {
        this.config = config;
    }

    private boolean isOk(HttpResponse<?> response) {
        return response.statusCode() / 100 == 2;
    }

    private <T> HttpResponse<T> verifyOk(HttpResponse<T> response) {
        if (!isOk(response)) {
            throw new RuntimeException(String.format("HTTP error (%d) downloading %s!", response.statusCode(),
                    response.request().uri().toString()));
        }
        return response;
    }

    private HttpClient newClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
//                .proxy(ProxySelector.of(new InetSocketAddress("www-proxy.com", 8080)))
//                .authenticator(Authenticator.getDefault())
                .build();
    }

    private HttpRequest newRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(config.getTimeout() > -1 ? config.getTimeout() : 30L))
                .build();
    }

    public InputStream getBlocking(String url) {
        logger.log(Level.FINE, "GET BIO: {0}", url);
        try {
            return verifyOk(
                    newClient().send(newRequest(url), HttpResponse.BodyHandlers.ofInputStream())).body();
        } catch (Throwable t) {
            throw new RuntimeException("Error downloading " + url, t);
        }
    }

    public void getNonBlocking(String url, CompletableFuture<InputStream> callback) {
        logger.log(Level.FINE, "GET NIO: {0}", url);
        try {
            newClient().sendAsync(newRequest(url), HttpResponse.BodyHandlers.ofInputStream())
                    .thenAccept(response -> callback.complete(response.body()));
        } catch (Throwable t) {
            throw new RuntimeException("Error downloading " + url, t);
        }
    }
}
