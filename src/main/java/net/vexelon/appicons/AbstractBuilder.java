package net.vexelon.appicons;

import net.vexelon.appicons.wireframe.Downloader;

public abstract class AbstractBuilder<BUILDER extends AbstractBuilder<BUILDER>> {

    @SuppressWarnings("unchecked") final BUILDER self() {
        return (BUILDER) this;
    }

    public BUILDER timeout(long timeout) {
        config().setTimeout(timeout);
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

    protected abstract BuilderConfig config();

    public abstract Downloader build();
}
