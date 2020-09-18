package net.vexelon.appicons;

import net.vexelon.appicons.utils.HashingUtils;
import net.vexelon.appicons.utils.HttpFetcher;
import net.vexelon.appicons.wireframe.AsyncDownloader;
import net.vexelon.appicons.wireframe.DownloadCallback;
import net.vexelon.appicons.wireframe.SyncDownloader;
import net.vexelon.appicons.wireframe.entities.IconFile;
import net.vexelon.appicons.wireframe.entities.IconURL;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDownloader<CONFIG extends BuilderConfig> implements SyncDownloader, AsyncDownloader {

    protected final CONFIG config;
    protected final HttpFetcher fetcher;

    public AbstractDownloader(CONFIG config) {
        this.config = config;
        this.fetcher = new HttpFetcher(config);
    }

    protected abstract String getAppUrl(String appId);

    protected abstract List<IconURL> parse(InputStream input);

    private IconFile toIconFile(IconURL iconURL, Path destination) {
        try (var input = fetcher.getBlocking(iconURL.getUrl())) {
            var type = iconURL.getType().toLowerCase();
            var copyToPath = destination.resolve(HashingUtils.sha1(iconURL.getUrl()) + "." + type);
            Files.copy(input, copyToPath, StandardCopyOption.REPLACE_EXISTING);

            var iconFile = new IconFile();
            iconFile.setPath(copyToPath.toString());
            iconFile.setExtension(type);
            iconFile.setWidth(iconURL.getWidth());
            iconFile.setHeight(iconURL.getHeight());
            return iconFile;
        } catch (IOException e) {
            throw new RuntimeException("Error fetching icon from url! " + iconURL.toString(), e);
        }
    }

    @Override
    public List<IconURL> getUrls(String appId) {
        return parse(fetcher.getBlocking(getAppUrl(appId)));
    }

    @Override
    public List<IconFile> getFiles(String appId, Path destination) {
        return getUrls(appId).stream().map(icon -> toIconFile(icon, destination)).collect(Collectors.toList());
    }

    @Override
    public Map<String, List<IconURL>> getMultiUrls(Set<String> appIds) {
        var result = new HashMap<String, List<IconURL>>();
        appIds.forEach(appId -> result.put(appId, parse(fetcher.getBlocking(getAppUrl(appId)))));
        return result;
    }

    @Override
    public Map<String, List<IconFile>> getMultiFiles(Set<String> appIds, Path destination) {
        return getMultiUrls(appIds).entrySet().stream().collect(Collectors.toMap(
                entry -> entry.getKey(),
                entry -> entry.getValue().stream().map(iconURL ->
                        toIconFile(iconURL, destination)).collect(Collectors.toList())
        ));
    }

    @Override
    public void getUrls(String appId, DownloadCallback<List<IconURL>> callback) {
        fetcher.getNonBlocking(getAppUrl(appId), new DownloadCallback<>() {
            @Override public void onError(String url, Throwable t) {
                callback.onError(appId, t);
            }

            @Override public void onSuccess(String url, InputStream inputStream) {
                callback.onSuccess(appId, parse(inputStream));
            }
        });
    }

    @Override
    public void getFiles(String appId, Path destination, DownloadCallback<List<IconFile>> callback) {
        // TODO
    }

    @Override
    public void getMultiUrls(Set<String> appIds, DownloadCallback<List<IconURL>> callback) {
        appIds.forEach(appId -> fetcher.getNonBlocking(getAppUrl(appId), new DownloadCallback<>() {
            @Override public void onError(String url, Throwable t) {
                callback.onError(appId, t);
            }

            @Override public void onSuccess(String url, InputStream inputStream) {
                callback.onSuccess(appId, parse(inputStream)); // TODO
            }
        }));
    }

    @Override
    public void getMultiFiles(Set<String> appIds, Path destination,
                              DownloadCallback<Map<String, List<IconFile>>> callback) {
        // TODO
    }
}
