package net.vexelon.appicons;

import net.vexelon.appicons.utils.HashingUtils;
import net.vexelon.appicons.utils.HttpFetcher;
import net.vexelon.appicons.wireframe.Downloader;
import net.vexelon.appicons.wireframe.entities.IconFile;
import net.vexelon.appicons.wireframe.entities.IconURL;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDownloader<CONFIG extends BuilderConfig> implements Downloader {

    protected final CONFIG config;
    protected final HttpFetcher fetcher;

    public AbstractDownloader(CONFIG config) {
        this.config = config;
        this.fetcher = new HttpFetcher(config);
    }

    protected abstract String getAppUrl(String appId);

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
    public List<IconFile> getFiles(String appId, Path destination) {
        return getUrls(appId).stream().map(icon -> toIconFile(icon, destination)).collect(Collectors.toList());
    }

    @Override
    public Map<String, List<IconFile>> getMultiFiles(Set<String> appIds, Path destination) {
        return getMultiUrls(appIds).entrySet().stream().collect(Collectors.toMap(
                entry -> entry.getKey(),
                entry -> entry.getValue().stream().map(iconURL ->
                        toIconFile(iconURL, destination)).collect(Collectors.toList())
        ));
    }
}
