package net.vexelon.appicons;

import net.vexelon.appicons.utils.FileUtils;
import net.vexelon.appicons.utils.HttpFetcher;
import net.vexelon.appicons.wireframe.BioDownloader;
import net.vexelon.appicons.wireframe.DownloadCallback;
import net.vexelon.appicons.wireframe.NioDownloader;
import net.vexelon.appicons.wireframe.entities.IconFile;
import net.vexelon.appicons.wireframe.entities.IconURL;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDownloader<CONFIG extends BuilderConfig> implements BioDownloader, NioDownloader {

    protected final CONFIG config;
    protected final HttpFetcher fetcher;

    public AbstractDownloader(CONFIG config) {
        this.config = config;
        this.fetcher = new HttpFetcher(config);
    }

    protected abstract String getAppUrl(String appId);

    protected abstract List<IconURL> parse(InputStream input);

    private IconFile toIconFile(String appId, IconURL iconURL, Path destination) {
        try (var input = fetcher.getBlocking(iconURL.getUrl())) {
            var copyToPath = destination.resolve(config.getNamingStrategyResolver().resolve(appId, iconURL));
            Files.copy(input, copyToPath, StandardCopyOption.REPLACE_EXISTING);

            var iconFile = new IconFile();
            iconFile.setPath(copyToPath.toString());
            iconFile.setExtension(iconURL.getType().toLowerCase());
            iconFile.setWidth(iconURL.getWidth());
            iconFile.setHeight(iconURL.getHeight());
            return iconFile;
        } catch (IOException e) {
            throw new RuntimeException("Error fetching icon from url! " + iconURL.toString(), e);
        }
    }

    private void toIconFiles(String appId, List<IconURL> iconUrls, Path destination, DownloadCallback<IconFile> callback) {
        iconUrls.forEach(iconURL -> {
            final var copyToPath = destination.resolve(config.getNamingStrategyResolver().resolve(
                    appId, iconURL));

            fetcher.getNonBlocking(iconURL.getUrl(), new DownloadCallback<>() {
                @Override
                public void onError(String innerAppId, Throwable t) {
                    callback.onError(appId, t);
                }

                @Override
                public void onSuccess(String innerAppId, InputStream inputStream) {
                    try {
                        var channel = AsynchronousFileChannel.open(
                                copyToPath, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

                        // XXX: readAllBytes() seems to fail in some cases. I was not able to find out why exactly.
//                        var buf = ByteBuffer.wrap(inputStream.readAllBytes());
                        var buf = ByteBuffer.wrap(FileUtils.readInputStreamFully(inputStream));

                        channel.write(buf, 0, channel, new CompletionHandler<>() {

                            @Override
                            public void completed(Integer result, AsynchronousFileChannel attachment) {
                                FileUtils.closeQuietly(attachment);
                                FileUtils.closeQuietly(inputStream);

                                var iconFile = new IconFile();
                                iconFile.setPath(copyToPath.toString());
                                iconFile.setExtension(iconURL.getType().toLowerCase());
                                iconFile.setWidth(iconURL.getWidth());
                                iconFile.setHeight(iconURL.getHeight());

                                callback.onSuccess(appId, iconFile);
                            }

                            @Override
                            public void failed(Throwable exc, AsynchronousFileChannel attachment) {
                                FileUtils.closeQuietly(attachment);
                                FileUtils.closeQuietly(inputStream);

                                callback.onError(appId, exc);
                            }
                        });
                    } catch (Throwable t) {
                        callback.onError(appId, new RuntimeException("Failed for url - " + iconURL.getUrl(), t));
                    }
                }
            });
        });
    }

    @Override
    public List<IconURL> getUrls(String appId) {
        return parse(fetcher.getBlocking(getAppUrl(appId)));
    }

    @Override
    public List<IconFile> getFiles(String appId, Path destination) {
        return getUrls(appId).stream().map(icon -> toIconFile(appId, icon, destination)).collect(Collectors.toList());
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
                Map.Entry::getKey,
                entry -> entry.getValue().stream().map(iconURL ->
                        toIconFile(entry.getKey(), iconURL, destination)).collect(Collectors.toList())
        ));
    }

    @Override
    public void getUrls(String appId, DownloadCallback<List<IconURL>> callback) {
        fetcher.getNonBlocking(getAppUrl(appId), new DownloadCallback<>() {
            @Override
            public void onError(String url, Throwable t) {
                callback.onError(appId, t);
            }

            @Override
            public void onSuccess(String url, InputStream inputStream) {
                callback.onSuccess(appId, parse(inputStream));
            }
        });
    }

    @Override
    public void getFiles(String appId, Path destination, DownloadCallback<IconFile> callback) {
        getUrls(appId, new DownloadCallback<>() {
            @Override public void onError(String appId, Throwable t) {
                callback.onError(appId, t);
            }

            @Override public void onSuccess(String appId, List<IconURL> iconUrls) {
                toIconFiles(appId, iconUrls, destination, callback);
            }
        });
    }

    @Override
    public void getMultiUrls(Set<String> appIds, DownloadCallback<List<IconURL>> callback) {
        appIds.forEach(appId -> fetcher.getNonBlocking(getAppUrl(appId), new DownloadCallback<>() {
            @Override
            public void onError(String url, Throwable t) {
                callback.onError(appId, t);
            }

            @Override
            public void onSuccess(String url, InputStream inputStream) {
                callback.onSuccess(appId, parse(inputStream));
            }
        }));
    }

    @Override
    public void getMultiFiles(Set<String> appIds, Path destination, DownloadCallback<IconFile> callback) {
        getMultiUrls(appIds, new DownloadCallback<>() {
            @Override
            public void onError(String appId, Throwable t) {
                callback.onError(appId, t);
            }

            @Override
            public void onSuccess(String appId, List<IconURL> iconUrls) {
                toIconFiles(appId, iconUrls, destination, callback);
            }
        });
    }

}
