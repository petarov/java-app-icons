package net.vexelon.appicons.utils;

import net.vexelon.appicons.BuilderConfig;

import java.io.IOException;
import java.net.*;
import java.util.List;

public class ProxySelectorConfig extends ProxySelector {

    private BuilderConfig config;
    private List<Proxy> proxies;

    public ProxySelectorConfig(BuilderConfig config) {
        this.config = config;
    }

    @Override
    public List<Proxy> select(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("URI can't be null!");
        }

        if (proxies == null) {
            if (config.getProxyType() == BuilderConfig.ProxyType.HTTP) {
                proxies = List.of(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(config.getProxyHost(), config.getProxyPort())));
            } else if (config.getProxyType() != BuilderConfig.ProxyType.NONE) {
                throw new IllegalArgumentException(config.getProxyType() + " is not a supported proxy type!");
            }
        }

        return proxies;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ex) {
        if (uri == null || sa == null || ex == null) {
            throw new IllegalArgumentException("Arguments can't be null.");
        }
        throw new RuntimeException("Error connecting to " + uri.toString() + " using proxy!", ex);
    }
}
