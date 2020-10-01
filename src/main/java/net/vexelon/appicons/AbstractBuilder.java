package net.vexelon.appicons;

import net.vexelon.appicons.wireframe.NamingStrategyResolver;
import net.vexelon.appicons.wireframe.NioDownloader;
import net.vexelon.appicons.wireframe.BioDownloader;

import java.util.concurrent.ExecutorService;

public abstract class AbstractBuilder<BUILDER extends AbstractBuilder<BUILDER>> {

    @SuppressWarnings("unchecked") final BUILDER self() {
        return (BUILDER) this;
    }

    public BUILDER timeout(long timeout) {
        config().setTimeout(timeout);
        return self();
    }

    public BUILDER userAgent(String userAgent) {
        config().setUserAgent(userAgent);
        return self();
    }

    public BUILDER country(String country) {
        config().setCountry(country);
        return self();
    }

    public BUILDER language(String language) {
        config().setLanguage(language);
        return self();
    }

    public BUILDER proxy(BuilderConfig.ProxyType type, String host, int port) {
        config().setProxyType(type);
        config().setProxyHost(host);
        config().setProxyPort(port);
        return self();
    }

    public BUILDER proxy(BuilderConfig.ProxyType type, String host, int port, String username, String password) {
        proxy(type, host, port);
        config().setProxyUser(username);
        config().setProxyPassword(password);
        return self();
    }

    public BUILDER skipSSLVerify(boolean skipSSLVerify) {
        config().setSkipSSLVerify(skipSSLVerify);
        return self();
    }

    public BUILDER namingStrategy(BuilderConfig.NamingStrategy namingStrategy) {
        config().setNamingStrategyResolver(namingStrategy);
        return self();
    }

    public BUILDER namingStrategy(NamingStrategyResolver namingStrategyResolver) {
        config().setNamingStrategyResolver(namingStrategyResolver);
        return self();
    }

    protected abstract BuilderConfig config();

    public abstract BioDownloader build();

    public abstract NioDownloader buildNio(ExecutorService executorService);
}
