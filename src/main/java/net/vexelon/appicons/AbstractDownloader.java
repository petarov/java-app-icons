package net.vexelon.appicons;

import net.vexelon.appicons.utils.HttpFetcher;
import net.vexelon.appicons.wireframe.Downloader;

public abstract class AbstractDownloader<CONFIG extends BuilderConfig> implements Downloader {

    protected final CONFIG config;
    protected final HttpFetcher fetcher;

    public AbstractDownloader(CONFIG config) {
        this.config = config;
        this.fetcher = new HttpFetcher(config);
    }

    protected abstract String getAppUrl(String appId);
}
