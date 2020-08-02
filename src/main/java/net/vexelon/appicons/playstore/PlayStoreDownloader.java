package net.vexelon.appicons.playstore;

import net.vexelon.appicons.entities.FileIcon;
import net.vexelon.appicons.entities.UrlIcon;
import net.vexelon.appicons.utils.HttpFetcher;
import net.vexelon.appicons.utils.UrlUtils;
import net.vexelon.appicons.wireframe.DownloadCallback;
import net.vexelon.appicons.wireframe.Downloader;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class PlayStoreDownloader implements Downloader {

    private HttpFetcher fetcher;
    private PlayStoreIconsBuilder.PlayStoreConfig config;

    public PlayStoreDownloader(HttpFetcher fetcher, PlayStoreIconsBuilder.PlayStoreConfig config) {
        this.fetcher = fetcher;
        this.config = config;
    }

    @Override
    public List<UrlIcon> getUrls(String appId) {
        return new PlayStoreIconsParser(config).parse(
                fetcher.getBlocking(UrlUtils.playstore(appId, "", "")));
    }

    @Override
    public List<FileIcon> getFiles(String appId, Path destination) {
        return null;
    }

    @Override
    public Map<String, List<UrlIcon>> getMultiUrls(Set<String> appIds) {
        return null;
    }

    @Override
    public Map<String, List<FileIcon>> getMultiFiles(Set<String> appIds, Path destination) {
        return null;
    }

    @Override
    public void getUrls(String appId, CompletableFuture<DownloadCallback<List<UrlIcon>>> callback) {

    }

    @Override
    public void getFiles(String appId, Path destination,
                         CompletableFuture<DownloadCallback<List<FileIcon>>> callback) {

    }

    @Override
    public void getMultiUrls(String appId,
                             CompletableFuture<DownloadCallback<Map<String, List<UrlIcon>>>> callback) {

    }

    @Override
    public void getMultiFiles(String appId, Path destination,
                              CompletableFuture<DownloadCallback<Map<String, List<FileIcon>>>> callback) {

    }
}
