package net.vexelon.appicons;

import net.vexelon.appicons.utils.HttpFetcher;
import net.vexelon.appicons.wireframe.Downloader;

public abstract class AbstractAppIconsBuilder<T extends Downloader> {

    private long timeout;
    private String country;
    private String language;

    public AbstractAppIconsBuilder<T> timeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public AbstractAppIconsBuilder<T> setCountry(String country) {
        this.country = country;
        return this;
    }

    public AbstractAppIconsBuilder<T> setLanguage(String language) {
        this.language = language;
        return this;
    }

    protected HttpFetcher newHttpFetcher() {
        var httpFetcher = new HttpFetcher();
        httpFetcher.setTimeout(timeout);
        return httpFetcher;
    }

    public abstract Downloader build();
}
