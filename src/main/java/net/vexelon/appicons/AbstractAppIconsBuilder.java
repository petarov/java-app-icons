package net.vexelon.appicons;

import net.vexelon.appicons.utils.HttpFetcher;
import net.vexelon.appicons.wireframe.Downloader;

public abstract class AbstractAppIconsBuilder<BUILDER extends AbstractAppIconsBuilder<BUILDER>> {

    // TODO proxy

    private long timeout = -1;
    private String country;
    private String language;

    @SuppressWarnings("unchecked") final BUILDER self() {
        return (BUILDER) this;
    }

    protected HttpFetcher newHttpFetcher() {
        var httpFetcher = new HttpFetcher();
        if (timeout != -1) {
            httpFetcher.setTimeout(timeout);
        }
        return httpFetcher;
    }

    public BUILDER timeout(long timeout) {
        this.timeout = timeout;
        return self();
    }

    public BUILDER setCountry(String country) {
        this.country = country;
        return self();
    }

    public BUILDER setLanguage(String language) {
        this.language = language;
        return self();
    }

    public abstract Downloader build();
}
