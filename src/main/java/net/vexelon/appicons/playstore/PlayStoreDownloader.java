package net.vexelon.appicons.playstore;

import net.vexelon.appicons.AbstractDownloader;
import net.vexelon.appicons.utils.AppURLUtils;
import net.vexelon.appicons.wireframe.DownloadCallback;
import net.vexelon.appicons.wireframe.entities.FileIcon;
import net.vexelon.appicons.wireframe.entities.URLIcon;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class PlayStoreDownloader extends AbstractDownloader<PlayStoreBuilder.PlayStoreConfig> {

    public PlayStoreDownloader(PlayStoreBuilder.PlayStoreConfig config) {
        super(config);
    }

    @Override
    protected String getAppUrl(String appId) {
        return AppURLUtils.playstore(appId, config.getCountry(), config.getLanguage());
    }

    @Override
    public List<URLIcon> getUrls(String appId) {
        return new PlayStoreParser(config).parse(fetcher.getBlocking(getAppUrl(appId)));
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
