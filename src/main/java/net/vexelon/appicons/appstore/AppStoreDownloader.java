package net.vexelon.appicons.appstore;

import net.vexelon.appicons.entities.FileIcon;
import net.vexelon.appicons.entities.URLIcon;
import net.vexelon.appicons.utils.AppURLUtils;
import net.vexelon.appicons.utils.HttpFetcher;
import net.vexelon.appicons.wireframe.DownloadCallback;
import net.vexelon.appicons.wireframe.Downloader;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class AppStoreDownloader implements Downloader {

    private final AppStoreIconsBuilder.AppStoreConfig config;
    private final HttpFetcher fetcher;

    public AppStoreDownloader(AppStoreIconsBuilder.AppStoreConfig config) {
        this.config = config;
        this.fetcher = new HttpFetcher(config);
    }

    @Override
    public List<URLIcon> getUrls(String appId) {
        return new AppStoreIconsParser(config).parse(
                fetcher.getBlocking(AppURLUtils.appstore(appId, "", "")));
    }

    @Override
    public List<FileIcon> getFiles(String appId, Path destination) {
        return null;
    }

    @Override
    public Map<String, List<URLIcon>> getMultiUrls(Set<String> appIds) {
        return null;
    }

    @Override
    public Map<String, List<FileIcon>> getMultiFiles(Set<String> appIds, Path destination) {
        return null;
    }

    @Override
    public void getUrls(String appId, CompletableFuture<DownloadCallback<List<URLIcon>>> callback) {

    }

    @Override
    public void getFiles(String appId, Path destination,
                         CompletableFuture<DownloadCallback<List<FileIcon>>> callback) {

    }

    @Override
    public void getMultiUrls(String appId,
                             CompletableFuture<DownloadCallback<Map<String, List<URLIcon>>>> callback) {

    }

    @Override
    public void getMultiFiles(String appId, Path destination,
                              CompletableFuture<DownloadCallback<Map<String, List<FileIcon>>>> callback) {

    }
}
